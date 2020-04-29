package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.utils.MongoProxy;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public final class ParselDB {
  private static MongoProxy proxy = new MongoProxy();
  private static DB database;
  private static DBCollection userCollection, pdfCollection, queryCollection, snippetCollection;

  /**
   * Connect.
   * @param connString Connection String.
   */
  public static void connect(String connString) {
    proxy.connect(connString);
    database = proxy.getClient().getDB("parseltongue");
    userCollection = database.getCollection("user");
    pdfCollection = database.getCollection("pdf");
    queryCollection = database.getCollection("query");
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
   * Gets pdf by ID.
   * @param id  id.
   */
  public static PDFSchema getPDFByID(String id) {
    DBCursor res = pdfCollection.find(new BasicDBObject("_id", id));
    if (res.count() == 1) {
      res.next();
      PDFSchema ret = new PDFSchema(res.curr().get("_id").toString(),
          res.curr().get("user").toString(),
          res.curr().get("filename").toString(),
          Base64.getDecoder().decode(res.curr().get("data").toString()));
      return ret;
    } else {
      return null;
    }
  }

  /**
   * Gets user by ID.
   * @param user  user id.
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
   * Gets pdf by ID.
   * @param id  id.
   */
  public static QuerySchema getQueryByID(String id) {
    DBCursor res = queryCollection.find(new BasicDBObject("_id", id));
    if (res.count() == 1) {
      res.next();
      return new QuerySchema(res.curr().get("_id").toString(),
          res.curr().get("user").toString(),
          res.curr().get("queryString").toString(),
          new ArrayList<>());
      // TODO: Make files
    } else {
      return null;
    }
  }

  /**
   * Gets user by ID.
   * @param user  user id.
   */
  public static List<QuerySchema> getQueriesByUser(String user) {
    DBCursor res = queryCollection.find(new BasicDBObject("user", user));
    List<QuerySchema> ret = new ArrayList<>();
    while (res.hasNext()) {
      res.next();
      // TODO: Get files as List.
      QuerySchema query = new QuerySchema(res.curr().get("_id").toString(),
          res.curr().get("user").toString(),
          res.curr().get("queryString").toString(),
          new ArrayList<>());
      ret.add(query);
    }
    return ret;
  }

  /**
   * Gets pdf by ID.
   * @param qid  query_id.
   */
  public static List<SnippetSchema> getSnippetsByQuery(String qid) {
    DBCursor res = snippetCollection.find(new BasicDBObject("query_id", qid));
    List<SnippetSchema> ret = new ArrayList<>();
    while (res.hasNext()) {
      res.next();
      SnippetSchema curr = new SnippetSchema(res.curr().get("query_id").toString(),
          Double.parseDouble(res.curr().get("score").toString()),
          res.curr().get("content").toString());
      ret.add(curr);
    }
    return ret;
  }

  /**
   * Updates or adds the given user to the DB.
   * @param user  user schema.
   */
  public static void updateUser(UserSchema user) {
    userCollection.insert(user.getDBObject());
  }

  /**
   * Updates or adds the given pdf to the DB.
   * @param pdf pdf schema.
   */
  public static void updatePDF(PDFSchema pdf) {
    pdfCollection.insert(pdf.getDBObject());
  }

  /**
   * Updates or adds the given pdf to the DB.
   * @param query query schema.
   */
  public static void updateQuery(QuerySchema query) {
    queryCollection.insert(query.getDBObject());
  }

  /**
   * Updates or adds the given user to the DB.
   * @param snippet snippet schema.
   */
  public static void updateSnippet(SnippetSchema snippet) {
    snippetCollection.insert(snippet.getDBObject());
  }

  /**
   * Removed use by ID.
   * @param _id user id.
   */
  public static void removeUserByID(String _id) {
    userCollection.remove(new BasicDBObject("_id", _id));
  }

  /**
   * Removes PDF by ID.
   * @param _id pdf id.
   */
  public static void removePDFByID(String _id) {
    pdfCollection.remove(new BasicDBObject("_id", _id));
  }

  /**
   * Removes PDF by ID.
   * @param _id pdf id.
   */
  public static void removeQueryByID(String _id) {
    queryCollection.remove(new BasicDBObject("_id", _id));
  }

  /**
   * Removes PDF by ID.
   * @param _id pdf id.
   */
  public static void removeSnippetByID(String _id) {
    snippetCollection.remove(new BasicDBObject("_id", _id));
  }

  /**
   * User Schema.
   */
  public static class UserSchema {
    private String _id, password;

    public UserSchema(String u, String pw) {
      _id = u;
      //TODO: Make this more secure.
      password = "" + pw;
    }

    public DBObject getDBObject() {
      return new BasicDBObject("_id", _id)
          .append("password", password);
    }

    public String get_id() {
      return _id;
    }

    public String getPassword() {
      return password;
    }
  }

  /**
   * PDF Schema.
   */
  public static class PDFSchema {
    private String _id, user, filename, data;

    public PDFSchema(String i, String u, String f, byte[] d) {
      _id = i;
      user = u;
      filename = f;
      data = Base64.getEncoder().encodeToString(d);
    }

    public DBObject getDBObject() {
      return new BasicDBObject("_id", _id)
          .append("user", user)
          .append("filename", filename)
          .append("data", data);
    }

    public String getId() {
      return _id;
    }

    public String getFilename() {
      return filename;
    }

    public String getUser() {
      return user;
    }

    public byte[] getData() {
      return Base64.getDecoder().decode(data);
    }
  }

  /**
   * Query Schema.
   */
  public static class QuerySchema {
    private String _id, user, queryString;
    private List<String> files;

    public QuerySchema(String i, String u, String qs, List<String> f) {
      _id = i;
      user = u;
      queryString = qs;
      files = f;
    }

    public DBObject getDBObject() {
      return new BasicDBObject()
          .append("_id", _id)
          .append("user", user)
          .append("queryString", queryString)
          .append("files", files);
    }

    public String get_id() {
      return _id;
    }

    public String getUser() {
      return user;
    }

    public String getQueryString() {
      return queryString;
    }

    public List<String> getFiles() {
      return files;
    }
  }

  /**
   * Snippet Schema.
   */
  public static class SnippetSchema {
    private String query_id, content;
    private double score;

    public SnippetSchema(String q, double s, String c) {
      query_id = q;
      score = s;
      content = c;
    }

    public DBObject getDBObject() {
      return new BasicDBObject()
          .append("query_id", query_id)
          .append("score", score)
          .append("content", content);
    }

    public String getContent() {
      return content;
    }

    public double getScore() {
      return score;
    }
  }
}



