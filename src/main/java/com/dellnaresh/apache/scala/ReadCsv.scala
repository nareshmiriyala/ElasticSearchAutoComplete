package com.dellnaresh.apache.scala

import java.io.{File, StringReader}
import java.net.InetAddress

import com.dellnaresh.elastic.JavaClient._
import com.fasterxml.jackson.databind.ObjectMapper
import com.opencsv.CSVReader
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.slf4j.LoggerFactory

/**
 * Created by nmiriyal on 29/07/2016.
 */

object ReadCsv extends java.io.Serializable{
  val log=LoggerFactory.getLogger(ReadCsv.getClass)
  val client= TransportClient.builder.build.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))


  //  def index(baby: Baby, client: Client): Unit = {
//    val jsonString: String = mapper.writeValueAsString(baby)
//    val indexResponse: IndexResponse = client.prepareIndex(INDEX_NAME, TYPE, baby.id).setSource(jsonString).get
//    println("Indexed document:" + indexResponse.getId)
//  }

  def readCsvFile() = {
    val conf: SparkConf = new SparkConf()
    conf.setMaster("local[*]").setAppName("BabyNameSearch")
    val sc: SparkContext = new SparkContext(conf)
    val inputFile=sc.textFile(csvPath("Nar.csv"));
    val result = inputFile.map{ line =>
      val reader = new CSVReader(new StringReader(line));
      val x = reader.readNext()
      new Baby(x(0),x(1),x(2),x(3),x(4))
    }
    val sortBy: RDD[Baby] = result.sortBy(baby=>baby.id)
    sortBy.foreach(baby => {
      log.info("Inserting baby:'{}'",baby)
      val mapper = new ObjectMapper()
      val jsonString: String = mapper.writeValueAsString(baby)
      val indexResponse: IndexResponse = client.prepareIndex(INDEX_NAME, TYPE, baby.id).setSource(jsonString).get
      log.info("Indexed document:'{}'" , indexResponse.getId)
    })
    client.close()

  }

  def csvPath(fileName:String):String={
    val classLoader: ClassLoader = getClass.getClassLoader
    val file: File = new File(classLoader.getResource(fileName).getFile)
    return file.getPath
  }
}
