package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseldb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Snippet Schema.
 */
public class SnippetSchema {
  private final String queryId, snippetId, content, file;
  private final double score;
  private final int page;

  /**
   * Constructor.
   *
   * @param q Query id.
   * @param i Snippet id.
   * @param c Content.
   * @param f File name.
   * @param s Score.
   * @param p Page.
   */
  public SnippetSchema(String q, String i, String c, String f, double s, int p) {
    queryId = q;
    snippetId = i;
    content = c;
    file = f;
    score = s;
    page = p;
  }

  /**
   * Get Snippet DBObject,
   *
   * @return Snippet DBObject.
   */
  public DBObject getDBObject() {
    return new BasicDBObject()
        .append("query_id", queryId)
        .append("snippet_id", snippetId)
        .append("content", content)
        .append("file", file)
        .append("score", score)
        .append("page", page);
  }

  /**
   * Get query id.
   * @return  Query id.
   */
  public String getQueryId() {
    return queryId;
  }

  /**
   * Get snippet id.
   * @return  Snippet id.
   */
  public String getSnippetId() {
    return snippetId;
  }

  /**
   * Get content.
   * @return Snippet content.
   */
  public String getContent() {
    return content;
  }

  /**
   * Get file.
   * @return  Source file.
   */
  public String getFile() {
    return file;
  }

  /**
   * Get score.
   * @return  Score.
   */
  public double getScore() {
    return score;
  }

  /**
   * Get page.
   * @return  Page num.
   */
  public int getPage() {
    return page;
  }
}
