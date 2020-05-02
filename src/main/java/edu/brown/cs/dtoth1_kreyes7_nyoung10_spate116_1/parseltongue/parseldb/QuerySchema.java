package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;

/**
 * Query Schema.
 */
public class QuerySchema {
  private final String id, user, label, queryString;
  private final List<String> files;

  /**
   * Constructor.
   *
   * @param i  Query ID.
   * @param u  User.
   * @param qs Query string.
   * @param f  Files.
   */
  public QuerySchema(String i, String u, String l, String qs, List<String> f) {
    id = i;
    user = u;
    label = l;
    queryString = qs;
    files = f;
  }

  /**
   * Return Query DBObject.
   *
   * @return Query Object.
   */
  public DBObject getDBObject() {
    return new BasicDBObject()
        .append("_id", id)
        .append("user", user)
        .append("label", label)
        .append("queryString", queryString)
        .append("files", files);
  }

  /**
   * Get ID.
   *
   * @return ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Get user.
   *
   * @return User.
   */
  public String getUser() {
    return user;
  }

  /**
   * Get label.
   *
   * @return  Label.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Get query string.
   *
   * @return Query string.
   */
  public String getQueryString() {
    return queryString;
  }

  /**
   * Get files.
   *
   * @return Files.
   */
  public List<String> getFiles() {
    return files;
  }
}
