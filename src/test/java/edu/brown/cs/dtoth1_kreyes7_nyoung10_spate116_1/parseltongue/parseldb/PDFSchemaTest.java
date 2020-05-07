package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * PDF Schema testing.
 */
public class PDFSchemaTest {
  private PDFSchema pdfSchema;

  /**
   * Create PDF Schema.
   */
  @Before
  public void setUp() {
    pdfSchema = new PDFSchema("id test", "username test", "filename test", new byte[]{});
  }

  /**
   * Destroy PDF Schema.
   */
  @After
  public void tearDown() {
    pdfSchema = null;
  }

  /**
   * Test getters.
   */
  @Test
  public void getterTest() {
    setUp();
    DBObject object =  new BasicDBObject("_id", "id test")
        .append("user", "username test")
        .append("filename", "filename test")
        .append("data", "");
    assertEquals(pdfSchema.getDBObject(), object);
    assertEquals(pdfSchema.getId(), "id test");
    assertEquals(pdfSchema.getUser(), "username test");
    assertEquals(pdfSchema.getFilename(), "filename test");
    assertArrayEquals(pdfSchema.getData(), new byte[]{});
    tearDown();
  }
}
