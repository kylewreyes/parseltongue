package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParselDBTest {
  private final UserSchema testingUser = new UserSchema("userid", "userpassword");
  private final PDFSchema testingPDF1 =
      new PDFSchema("pdfid_1", "userid", "filename_1.pdf", new byte[]{});
  private final PDFSchema testingPDF2 =
      new PDFSchema("pdfid_2", "another user", "filename_2.pdf", new byte[]{});
  private final QuerySchema testingQuery1 = new QuerySchema(
      "queryid_1", "userid", "label_1", "query_1", new byte[]{}, new ArrayList<>());
  private final QuerySchema testingQuery2 = new QuerySchema(
      "queryid_2", "another user", "label_2", "query_2", new byte[]{}, new ArrayList<>());
  private final SnippetSchema testingSnippet1 = new SnippetSchema(
      "queryid_1", "snippetid_1", "content_1", "file_1", 2.0, 1);
  private final SnippetSchema testingSnippet2 = new SnippetSchema(
      "queryid_2", "snippetid_2", "content_2", "file_2", 2.0, 1);

  /**
   * Set up, populate DB with some users, pdfs, queries, and snippets.
   */
  @Before
  public void setUp() {
    String uri = "mongodb://n-young:OtAPchYZjVfhQIqZ@cluster0-shard-00-00-rtgum.mongodb.net:27017,cluster0-shard-00-01-rtgum.mongodb.net:27017,cluster0-shard-00-02-rtgum.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority";
    ParselDB.connect(uri);
    ParselDB.removeUserByID("userid");
    ParselDB.removePDFByID("pdfid_1");
    ParselDB.removePDFByID("pdfid_2");
    ParselDB.removeQueryByID("queryid_1");
    ParselDB.removeQueryByID("queryid_2");
    ParselDB.removeSnippetsByQuery("queryid_1");
    ParselDB.removeSnippetsByQuery("queryid_2");
    ParselDB.updateUser(testingUser);
    ParselDB.updatePDF(testingPDF1);
    ParselDB.updatePDF(testingPDF2);
    ParselDB.updateQuery(testingQuery1);
    ParselDB.updateQuery(testingQuery2);
    ParselDB.updateSnippet(testingSnippet1);
    ParselDB.updateSnippet(testingSnippet2);
  }

  /**
   * Tear down.
   */
  @After
  public void tearDown() {
    ParselDB.disconnect();
  }

  /**
   * Test that DBProxy connection throws no errors.
   */
  @Test
  public void getUserByIdTest() {
    setUp();
    UserSchema ret = ParselDB.getUserByID("userid");
    assertEquals(ret.getId(), "userid");
    assertEquals(ret.getPassword(), "userpassword");
    assertNull(ParselDB.getUserByID("userid doesn't exist"));
    tearDown();
  }

  /**
   * Test that DBProxy connection throws no errors.
   */
  @Test
  public void getPDFByIdTest() {
    setUp();
    PDFSchema ret = ParselDB.getPDFByID("pdfid_1");
    assertEquals(ret.getId(), "pdfid_1");
    assertEquals(ret.getUser(), "userid");
    assertEquals(ret.getFilename(), "filename_1.pdf");
    assertNull(ParselDB.getPDFByID("pdf doesn't exist"));
    tearDown();
  }

  /**
   * Test that DBProxy connection throws no errors.
   */
  @Test
  public void getPDFsByUserTest() {
    setUp();
    PDFSchema ret = ParselDB.getPDFsByUser("userid").get(0);
    assertEquals(ret.getId(), "pdfid_1");
    assertEquals(ret.getUser(), "userid");
    assertEquals(ret.getFilename(), "filename_1.pdf");
    assertEquals(ParselDB.getPDFsByUser("userid doesn't exist"), new ArrayList<>());
    tearDown();
  }

  /**
   * Test that DBProxy connection throws no errors.
   */
  @Test
  public void getQueryByIdTest() {
    setUp();
    QuerySchema ret = ParselDB.getQueryByID("queryid_1");
    assertEquals(ret.getId(), "queryid_1");
    assertEquals(ret.getUser(), "userid");
    assertEquals(ret.getLabel(), "label_1");
    assertEquals(ret.getQueryString(), "query_1");
    assertNull(ParselDB.getQueryByID("queryid doesn't exist"));
    tearDown();
  }

  /**
   * Test that DBProxy connection throws no errors.
   */
  @Test
  public void getQueriesByUserTest() {
    setUp();
    QuerySchema ret = ParselDB.getQueriesByUser("userid").get(0);
    assertEquals(ret.getId(), "queryid_1");
    assertEquals(ret.getUser(), "userid");
    assertEquals(ret.getLabel(), "label_1");
    assertEquals(ret.getQueryString(), "query_1");
    assertEquals(ParselDB.getQueriesByUser("userid doesn't exist"), new ArrayList<>());
    tearDown();
  }

  /**
   * Test that DBProxy connection throws no errors.
   */
  @Test
  public void getSnippetsByQueryTest() {
    setUp();
    SnippetSchema ret = ParselDB.getSnippetsByQuery("queryid_1").get(0);
    assertEquals(ret.getQueryId(), "queryid_1");
    assertEquals(ret.getSnippetId(), "snippetid_1");
    assertEquals(ret.getContent(), "content_1");
    assertEquals(ret.getFile(), "file_1");
    assertEquals(ParselDB.getSnippetsByQuery("queryid doesn't exist"), new ArrayList<>());
    tearDown();
  }
}
