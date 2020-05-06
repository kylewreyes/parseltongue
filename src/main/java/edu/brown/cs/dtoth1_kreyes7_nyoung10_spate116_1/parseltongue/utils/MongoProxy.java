package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * MongoDB Proxy.
 */
public class MongoProxy {
  private MongoClient mongoClient;

  /**
   * Connect.
   * @param connString  Connection String.
   */
  public void connect(String connString) {
    mongoClient = new MongoClient(new MongoClientURI(connString));
  }

  /**
   * Disconnect.
   */
  public void disconnect() {
    mongoClient.close();
  }

  /**
   * Get Client.
   * @return  mongoClient.
   */
  public MongoClient getClient() {
    return mongoClient;
  }
}
