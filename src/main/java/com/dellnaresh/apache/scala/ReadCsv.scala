package com.dellnaresh.apache.scala

import java.io.{StringReader, File}

import com.opencsv.CSVReader
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by nmiriyal on 29/07/2016.
 */
object ReadCsv {
  def readCsvFile={
    val conf: SparkConf = new SparkConf()
    conf.setMaster("local[*]").setAppName("BabyNameSearch")
    val sc: SparkContext = new SparkContext(conf)
    val inputFile=sc.textFile("C:\\EAI\\ElasticSearchAutoComplete\\src\\main\\resources\\NationalNames.csv");
    val result = inputFile.map{ line =>
      val reader = new CSVReader(new StringReader(line));
      val x = reader.readNext()
      new Baby(x(0),x(1),x(2),x(3),x(4))
    }
    val sortBy: RDD[Baby] = result.sortBy(baby=>baby.id).persist(StorageLevel.DISK_ONLY)
    sortBy.foreach(println)

  }
  def csvPath(fileName:String):String={
    val classLoader: ClassLoader = getClass.getClassLoader
    val file: File = new File(classLoader.getResource(fileName).getFile)
    return file.getAbsolutePath
  }
}
