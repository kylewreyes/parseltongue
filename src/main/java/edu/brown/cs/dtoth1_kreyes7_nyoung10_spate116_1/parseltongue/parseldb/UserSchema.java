package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * User Schema.
 */
public class UserSchema {
  private final String id, password;

  /**
   * Constructor.
   *
   * @param u  User id.
   * @param pw Password.
   */
  public UserSchema(String u, String pw) {
    id = u;
    password = pw;
  }

  /**
   * Get the User DBObject.
   *
   * @return User DBObject.
   */
  public DBObject getDBObject() {
    return new BasicDBObject("_id", id)
        .append("password", password);
  }

  /**
   * Get id.
   *
   * @return ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Get PW.
   *
   * @return Password.
   */
  public String getPassword() {
    return password;
  }
}
