package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * PDF Schema testing.
 */
public class SnippetSchemaTest {
  private SnippetSchema snippetSchema;

  /**
   * Create User Schema.
   */
  @Before
  public void setUp() {
    snippetSchema =
        new SnippetSchema("query test", "id test", "content test", "filename test", 1.0, 2);
  }

  /**
   * Destroy User Schema.
   */
  @After
  public void tearDown() {
    snippetSchema = null;
  }

  /**
   * Test getters.
   */
  @Test
  public void getterTest() {
    setUp();
    DBObject object =  new BasicDBObject()
        .append("query_id", "query test")
        .append("snippet_id", "id test")
        .append("content", "content test")
        .append("file", "filename test")
        .append("score", 1.0)
        .append("page", 2);
    assertEquals(snippetSchema.getDBObject(), object);
    assertEquals(snippetSchema.getQueryId(), "query test");
    assertEquals(snippetSchema.getSnippetId(), "id test");
    assertEquals(snippetSchema.getContent(), "content test");
    assertEquals(snippetSchema.getFile(), "filename test");
    assertEquals(snippetSchema.getScore(), 1.0, 0.01);
    assertEquals(snippetSchema.getPage(), 2);
    tearDown();
  }
}
