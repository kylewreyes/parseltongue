package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * MongoDB Proxy tests.
 */
public class MongoProxyTest {
  private MongoProxy proxy;

  /**
   * Set up.
   */
  @Before
  public void setUp() {
    proxy = new MongoProxy();
    String uri = "mongodb://n-young:OtAPchYZjVfhQIqZ@cluster0-shard-00-00-rtgum.mongodb.net:27017,cluster0-shard-00-01-rtgum.mongodb.net:27017,cluster0-shard-00-02-rtgum.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority";
    proxy.connect(uri);
  }

  /**
   * Tear down.
   */
  @After
  public void tearDown() {
    proxy = null;
  }

  /**
   * Test that DBProxy connection throws no errors.
   */
  @Test
  public void connectionTest() {
    setUp();
    assertNotNull(proxy);
    tearDown();
  }
}
