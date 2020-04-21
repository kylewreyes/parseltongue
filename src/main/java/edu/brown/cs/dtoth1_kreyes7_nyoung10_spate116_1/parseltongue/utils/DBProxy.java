package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Generic database proxy class, which handles db connection, query execution,
 * and caching.
 */
public class DBProxy {
  private static Connection conn;

  /**
   * Connects to the database.
   *
   * @param filename File to connect DB to.
   * @throws SQLException           Exception.
   * @throws ClassNotFoundException Exception.
   */
  public static void connect(String filename) {
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }

  /**
   * Queries for DB connection.
   *
   * @return True if DB connected, false if not.
   */
  public static boolean isConnected() {
    return conn != null;
  }

  /**
   * Executes SQL SELECT Query directly.
   *
   * @param sqlCommand SQL Command.
   * @return SQL output.
   */
  public static List<List<String>> executeQuery(String sqlCommand) {
    try {
      if (isConnected()) {
        // Create a prepared statement.
        PreparedStatement prep = conn.prepareStatement(sqlCommand);
        ResultSet rs = prep.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        List<List<String>> result = new ArrayList<>();
        // Create an array of array of strings from the DB.
        while (rs.next()) {
          ArrayList<String> row = new ArrayList<>();
          for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
            row.add("" + rs.getObject(i));
          }
          result.add(row);
        }
        // Close the prepared statement.
        prep.close();
        return result;
      } else {
        throw new Exception("Database not connected.");
      }
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
      return null;
    }
  }

  /**
   * Executes SQL INSERT Query directly.
   *
   * @param sqlCommand SQL Command.
   * @return SQL output.
   */
  public static int executeUpdate(String sqlCommand) {
    try {
      if (isConnected()) {
        // Create a prepared statement.
        PreparedStatement prep = conn.prepareStatement(sqlCommand);
        int ret = prep.executeUpdate();
        prep.close();
        return ret;
      } else {
        throw new Exception("Database not connected.");
      }
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
      return 0;
    }
  }
}
