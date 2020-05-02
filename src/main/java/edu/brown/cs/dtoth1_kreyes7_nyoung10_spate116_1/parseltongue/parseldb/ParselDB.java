package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.MongoProxy;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * ParselTongue DB Class.
 */
public final class ParselDB {
  private static final MongoProxy PROXY = new MongoProxy();
  private static DBCollection userCollection, pdfCollection, queryCollection, snippetCollection;

  /**
   * Private constructor.
   */
  private ParselDB() {
  }

  /**
   * Connect to MongoDB and set the collecctions.
   *
   * @param connString Connection String.
   */
  public static void connect(String connString) {
    PROXY.connect(connString);
    DB database = PROXY.getClient().getDB("parseltongue");
    userCollection = database.getCollection("user");
    pdfCollection = database.getCollection("pdf");
    queryCollection = database.getCollection("query");
    snippetCollection = database.getCollection("snippet");
  }

  /**
   * Disconnect from MongoDB.
   */
  public static void disconnect() {
    PROXY.disconnect();
  }

  /**
   * Gets a user by ID.
   *
   * @param id User id.
   * @return User with the given ID.
   */
  public static UserSchema getUserByID(String id) {
    DBCursor res = userCollection.find(new BasicDBObject("_id", id));
    if (res.count() == 1) {
      res.next();
      return new UserSchema(res.curr().get("_id").toString(),
          res.curr().get("password").toString());
    } else {
      return null;
    }
  }

  /**
   * Gets a PDF by ID.
   *
   * @param id PDF id.
   * @return PDF with the given ID.
   */
  public static PDFSchema getPDFByID(String id) {
    DBCursor res = pdfCollection.find(new BasicDBObject("_id", id));
    if (res.count() == 1) {
      res.next();
      return new PDFSchema(res.curr().get("_id").toString(),
          res.curr().get("user").toString(),
          res.curr().get("filename").toString(),
          Base64.getDecoder().decode(res.curr().get("data").toString()));
    } else {
      return null;
    }
  }

  /**
   * Gets all PDFs that belong to the given user.
   *
   * @param user User id.
   * @return List of PDFs belonging to the given user.
   */
  public static List<PDFSchema> getPDFsByUser(String user) {
    DBCursor res = pdfCollection.find(new BasicDBObject("user", user));
    List<PDFSchema> ret = new ArrayList<>();
    while (res.hasNext()) {
      res.next();
      PDFSchema pdf = new PDFSchema(res.curr().get("_id").toString(),
          res.curr().get("user").toString(),
          res.curr().get("filename").toString(),
          Base64.getDecoder().decode(res.curr().get("data").toString()));
      ret.add(pdf);
    }
    return ret;
  }

  /**
   * Gets a query by ID. NOTE: Doesn't return the files.
   * TODO: Get files
   *
   * @param id Query id.
   * @return Query with the given ID.
   */
  public static QuerySchema getQueryByID(String id) {
    DBCursor res = queryCollection.find(new BasicDBObject("_id", id));
    if (res.count() == 1) {
      res.next();
      return new QuerySchema(res.curr().get("_id").toString(),
          res.curr().get("user").toString(),
          res.curr().get("label").toString(),
          res.curr().get("queryString").toString(),
          Base64.getDecoder().decode(res.curr().get("data").toString()),
          new ArrayList<>());
    } else {
      return null;
    }
  }

  /**
   * Gets all queries that belong to the given user.
   * TODO: Get files.
   *
   * @param user User id.
   * @return Queries that belong to the given user.
   */
  public static List<QuerySchema> getQueriesByUser(String user) {
    DBCursor res = queryCollection.find(new BasicDBObject("user", user));
    List<QuerySchema> ret = new ArrayList<>();
    while (res.hasNext()) {
      res.next();
      QuerySchema query = new QuerySchema(res.curr().get("_id").toString(),
          res.curr().get("user").toString(),
          res.curr().get("label").toString(),
          res.curr().get("queryString").toString(),
          Base64.getDecoder().decode(res.curr().get("data").toString()),
          new ArrayList<>());
      ret.add(0, query);
    }
    return ret;
  }

  /**
   * Gets all snippets that belong to the given query.
   *
   * @param qid Query id.
   * @return Snippets that belong to the given query.
   */
  public static List<SnippetSchema> getSnippetsByQuery(String qid) {
    DBCursor res = snippetCollection.find(new BasicDBObject("query_id", qid));
    List<SnippetSchema> ret = new ArrayList<>();
    while (res.hasNext()) {
      res.next();
      SnippetSchema curr = new SnippetSchema(res.curr().get("query_id").toString(),
          res.curr().get("snippet_id").toString(),
          res.curr().get("content").toString(),
          res.curr().get("file").toString(),
          Double.parseDouble(res.curr().get("score").toString()),
          Integer.parseInt(res.curr().get("page").toString()));
      ret.add(curr);
    }
    return ret;
  }

  /**
   * Updates or adds the given user to the DB.
   *
   * @param user user schema.
   */
  public static void updateUser(UserSchema user) {
    userCollection.insert(user.getDBObject());
  }

  /**
   * Updates or adds the given pdf to the DB.
   *
   * @param pdf pdf schema.
   */
  public static void updatePDF(PDFSchema pdf) {
    pdfCollection.insert(pdf.getDBObject());
  }

  /**
   * Updates or adds the given query to the DB.
   *
   * @param query query schema.
   */
  public static void updateQuery(QuerySchema query) {
    queryCollection.insert(query.getDBObject());
  }

  /**
   * Updates or adds the given snippet to the DB.
   *
   * @param snippet snippet schema.
   */
  public static void updateSnippet(SnippetSchema snippet) {
    snippetCollection.insert(snippet.getDBObject());
  }

  /**
   * Removes user by ID.
   *
   * @param id user id.
   */
  public static void removeUserByID(String id) {
    userCollection.remove(new BasicDBObject("_id", id));
  }

  /**
   * Removes PDF by ID.
   *
   * @param id pdf id.
   */
  public static void removePDFByID(String id) {
    pdfCollection.remove(new BasicDBObject("_id", id));
  }

  /**
   * Removes PDF by ID.
   *
   * @param id pdf id.
   */
  public static void removeQueryByID(String id) {
    queryCollection.remove(new BasicDBObject("_id", id));
  }

  /**
   * Removes PDF by ID.
   *
   * @param queryId query id.
   */
  public static void removeSnippetsByQuery(String queryId) {
    snippetCollection.remove(new BasicDBObject("query_id", queryId));
  }

}
