package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PDFParser {

  public String getSnippets(String path) {
    File file = new File(path);
    return getSnippets(file);
  }

  public String getSnippets(File file) {
    final double WID_MARGIN = 36; // 0.5" in pts
    final double HT_MARGIN = 72; // 1" in pts

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
        String s = pdfStripper.getTextForRegion("core text area");
        System.out.println(s);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file");
    }
  }

  public static void main(String[] args) throws IOException {

  }
}
