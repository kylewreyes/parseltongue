package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Base64;

/**
 * PDF Schema.
 */
public class PDFSchema {
  private final String id, user, filename, data;

  /**
   * Constructor.
   *
   * @param i PDF id.
   * @param u User.
   * @param f Filename.
   * @param d Data.
   */
  public PDFSchema(String i, String u, String f, byte[] d) {
    id = i;
    user = u;
    filename = f;
    data = Base64.getEncoder().encodeToString(d);
  }

  /**
   * Get PDF DBObject.
   *
   * @return PDF DBObject.
   */
  public DBObject getDBObject() {
    return new BasicDBObject("_id", id)
        .append("user", user)
        .append("filename", filename)
        .append("data", data);
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
   * Get filename.
   *
   * @return Filename.
   */
  public String getFilename() {
    return filename;
  }

  /**
   * Get data.
   *
   * @return Data as a Base64 byte array.
   */
  public byte[] getData() {
    return Base64.getDecoder().decode(data);
  }
}
