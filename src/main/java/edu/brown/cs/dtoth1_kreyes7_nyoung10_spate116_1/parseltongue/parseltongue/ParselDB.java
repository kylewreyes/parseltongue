package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.MongoProxy;

public final class ParselDB {
  private static MongoProxy proxy = new MongoProxy();
  private static DB database;
  private static DBCollection userCollection, pdfCollection, snippetCollection;

  /**
   * Connect.
   * @param connString Connection String.
   */
  public static void connect(String connString) {
    proxy.connect(connString);
    database = proxy.getClient().getDB("parseltongue");
    userCollection = database.getCollection("user");
    pdfCollection = database.getCollection("pdf");
    snippetCollection = database.getCollection("snippet");
  }

  /**
   * Disconnect.
   */
  public static void disconnect() {
    proxy.disconnect();
  }

  /**
   * Gets user by ID.
   * @param id  id.
   */
  public static DBCursor getUserByID(String id) {
    return userCollection.find(new BasicDBObject("_id", id));
  }

  /**
   * Gets user by ID.
   * @param id  id.
   */
  public static DBCursor getUserByIDPW(String id, String password) {
    return userCollection.find(
        new BasicDBObject("_id", id).append("password", "" + password.hashCode()));
  }

  /**
   * Gets pdf by ID.
   * @param id  id.
   */
  public static DBCursor getPDFByID(String id) {
    return pdfCollection.find(new BasicDBObject("_id", id));
  }

  /**
   * Gets user by ID.
   * @param user  user id.
   */
  public static DBCursor getPDFByUser(String user) {
    return pdfCollection.find(new BasicDBObject("user", user));
  }

  /**
   * Gets snippets by PDF id.
   * @param pdf_id  pdf_id.
   */
  public static DBCursor getSnippetsByPDF(String pdf_id) {
    return snippetCollection.find(new BasicDBObject("pdf_id", pdf_id));
  }

  /**
   * Updates or adds the given user to the DB.
   * @param user
   */
  public static void updateUser(UserSchema user) {
    userCollection.insert(user.getDBObject());
  }

  /**
   * Updates or adds the given pdf to the DB.
   * @param pdf
   */
  public static void updatePDF(PDFSchema pdf) {
    pdfCollection.insert(pdf.getDBObject());
  }

  /**
   * Updates or adds the given user to the DB.
   * @param snippet
   */
  public static void updateSnippet(SnippetSchema snippet) {
    snippetCollection.insert(snippet.getDBObject());
  }

  /**
   * Updates or adds the given user to the DB.
   * @param user
   */
  public static void removeUser(UserSchema user) {
    userCollection.remove(user.getDBObject());
  }

  /**
   * Updates or adds the given pdf to the DB.
   * @param pdf
   */
  public static void removePDF(PDFSchema pdf) {
    pdfCollection.remove(pdf.getDBObject());
  }

  /**
   * Updates or adds the given user to the DB.
   * @param snippet
   */
  public static void removeSnippet(SnippetSchema snippet) {
    snippetCollection.remove(snippet.getDBObject());
  }

  /**
   * User Schema.
   */
  public static class UserSchema {
    private String _id, password;

    public UserSchema(String u, String pw) {
      _id = u;
      //TODO: Make this more secure.
      password = "" + pw.hashCode();
    }

    public DBObject getDBObject() {
      return new BasicDBObject("_id", _id)
          .append("password", password);
    }
  }

  /**
   * PDF Schema.
   */
  public static class PDFSchema {
    String _id, user, filename, query;

    public PDFSchema(String i, String u, String f, String q) {
      _id = i;
      user = u;
      filename = f;
      query = q;
    }

    public DBObject getDBObject() {
      return new BasicDBObject("_id", _id)
          .append("user", user)
          .append("filename", filename)
          .append("query", query);
    }
  }

  /**
   * Snippet Schema.
   */
  public static class SnippetSchema {
    String pdf_id, content;
    double score;

    public SnippetSchema(String p, double s, String c) {
      pdf_id = p;
      score = s;
      content = c;
    }

    public DBObject getDBObject() {
      return new BasicDBObject()
          .append("pdf_id", pdf_id)
          .append("score", score)
          .append("content", content);
    }
  }


}



