package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

/**
 * PDF Parser Class! TODO: Complete Docs.
 */
public class PDFParser {

  /**
   * Get Text. TODO: Complete Docs.
   * @param path  Path to file.
   * @return  Text.
   */
  public String getText(String path) {
    File file = new File(path);
    return getText(file);
  }

  /**
   * Get Text. TODO: Complete Docs.
   * @param file  Path to file.
   * @return  Text.
   */
  public String getText(File file) {
    final double WID_MARGIN = 36; // 0.5" in pts
    final double HT_MARGIN = 72; // 1" in pts
    StringBuilder text = new StringBuilder();

    try (PDDocument document = PDDocument.load(file)) {
      PDRectangle docSize = document.getPage(0).getMediaBox();
      double newWidth = docSize.getWidth() - 2 * WID_MARGIN;
      double newHeight = docSize.getHeight() - 2 * HT_MARGIN;
      Rectangle2D.Double boundingBox = new Rectangle2D.Double(WID_MARGIN, HT_MARGIN, newWidth,
          newHeight);
      PDFTextStripperByArea pdfStripper = new PDFTextStripperByArea();
      pdfStripper.addRegion("core text area", boundingBox);
      for (PDPage p : document.getPages()) {
        pdfStripper.extractRegions(p);
        text.append(pdfStripper.getTextForRegion("core text area"));
        text.append(System.lineSeparator());
      }
      // Remove the last line separator
      text.setLength(text.length() - 1);
      return text.toString();
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file");
    }
  }
}
