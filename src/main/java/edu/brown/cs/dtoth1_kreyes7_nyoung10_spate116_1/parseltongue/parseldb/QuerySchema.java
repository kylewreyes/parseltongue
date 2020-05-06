package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Base64;
import java.util.List;

/**
 * Query Schema.
 */
public class QuerySchema {
  private final String id, user, label, queryString, data;
  private final List<String> files;

  /**
   * Constructor.
   *
   * @param i  Query ID.
   * @param u  User.
   * @param l TODO.
   * @param qs Query string.
   * @param d TODO.
   * @param f  Files.
   */
  public QuerySchema(String i, String u, String l, String qs, byte[] d, List<String> f) {
    id = i;
    user = u;
    label = l;
    queryString = qs;
    data = Base64.getEncoder().encodeToString(d);
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
        .append("data", data)
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
   * Get graph data.
   * @return  Graph data.
   */
  public byte[] getData() {
    return Base64.getDecoder().decode(data);
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
