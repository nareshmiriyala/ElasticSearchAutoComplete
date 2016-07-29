package com.dellnaresh.apache.scala

import com.dellnaresh.elastic.JavaClient
import org.scalatest.FunSuite


/**
 * Created by nmiriyal on 29/07/2016.
 */
class ReadCsv$Test extends FunSuite {
  test("readcsv should return rdd"){
    ReadCsv.readCsvFile
  }
}
