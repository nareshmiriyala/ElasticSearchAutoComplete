package com.dellnaresh.apache.scala

import java.sql.DriverManager

/**
 * Created by nmiriyal on 29/07/2016.
 */
object ReadDatabase {
  def createConnection() = {
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    DriverManager.getConnection("jdbc:mysql://localhost/test?user=holden");
  }
}
