package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * PDF Schema testing.
 */
public class QuerySchemaTest {
  private QuerySchema querySchema;

  /**
   * Create User Schema.
   */
  @Before
  public void setUp() {
    querySchema = new QuerySchema(
        "id test", "user test", "label test", "query test", new byte[]{}, new ArrayList<>());
  }

  /**
   * Destroy User Schema.
   */
  @After
  public void tearDown() {
    querySchema = null;
  }

  /**
   * Test getters.
   */
  @Test
  public void getterTest() {
    setUp();
    DBObject object =  new BasicDBObject()
        .append("_id", "id test")
        .append("user", "user test")
        .append("label", "label test")
        .append("queryString", "query test")
        .append("data", "")
        .append("files", new ArrayList<String>());
    assertEquals(querySchema.getDBObject(), object);
    assertEquals(querySchema.getId(), "id test");
    assertEquals(querySchema.getUser(), "user test");
    assertEquals(querySchema.getLabel(), "label test");
    assertEquals(querySchema.getQueryString(), "query test");
    assertArrayEquals(querySchema.getData(), new byte[]{});
    assertEquals(querySchema.getFiles(), new ArrayList<String>());
    tearDown();
  }
}
