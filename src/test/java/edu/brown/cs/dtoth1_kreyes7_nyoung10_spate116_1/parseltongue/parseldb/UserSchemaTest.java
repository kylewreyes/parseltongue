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
public class UserSchemaTest {
  private UserSchema userSchema;

  /**
   * Create User Schema.
   */
  @Before
  public void setUp() {
    userSchema = new UserSchema("username test", "password test");
  }

  /**
   * Destroy User Schema.
   */
  @After
  public void tearDown() {
    userSchema = null;
  }

  /**
   * Test getters.
   */
  @Test
  public void getterTest() {
    setUp();
    DBObject object =  new BasicDBObject("_id", "username test")
        .append("password", "password test");
    assertEquals(userSchema.getDBObject(), object);
    assertEquals(userSchema.getId(), "username test");
    assertEquals(userSchema.getPassword(), "password test");
    tearDown();
  }
}
