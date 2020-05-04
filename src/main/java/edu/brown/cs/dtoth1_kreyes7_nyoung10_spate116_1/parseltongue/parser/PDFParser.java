package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * A {@link SourceParser} specific for PDFs.
 */
public class PDFParser implements SourceParser {
  private final PDDocument document;

  /**
   * Creates a new PDFParser.
   * @param path the location of the PDF file
   */
  public PDFParser(String path) {
    this(new File(path));
  }

  /**
   * Creates a new PDFParser.
   * @param file the PDF file
   */
  public PDFParser(File file) {
    try {
      document = PDDocument.load(file);
    } catch (IOException e) {
      throw new IllegalArgumentException("ERROR: Invalid file");
    }
  }

  /**
   * Gets the page count of the current PDF.
   * @return the number of pages in the given PDF
   */
  public int getPageCount() {
    return document.getNumberOfPages();
  }

  @Override
  public String getText() {
    // TODO: these aren't used.
    final double widthMargin = 36; // 0.5" in pts
    final double htMargin = 72; // 1" in pts
    StringBuilder text = new StringBuilder();
    for (int i = 1; i <= document.getNumberOfPages(); i++) {
      text.append(getTextFromPage(i));
      text.append(System.lineSeparator());
    }
    // Remove the last line separator
    text.setLength(text.length() - 1);
    return text.toString();
  }

  /**
   * Gets the text from a certain page of a PDF. Note that page indexing starts at 1.
   * @param pageNum the page number of the specific page from the PDF
   * @return the text from the specified page of the PDF
   */
  public String getTextFromPage(int pageNum) {
    final double widthMargin = 36; // 0.5" in pts
    final double htMargin = 72; // 1" in pts

    if (pageNum <= document.getNumberOfPages()) {
      PDRectangle docSize = document.getPage(pageNum - 1).getMediaBox();
      double newWidth = docSize.getWidth() - 2 * widthMargin;
      double newHeight = docSize.getHeight() - 2 * htMargin;
      Rectangle2D.Double boundingBox = new Rectangle2D.Double(widthMargin, htMargin, newWidth,
          newHeight);
      try {
        PDFTextStripperByArea pdfStripper = new PDFTextStripperByArea();
        pdfStripper.addRegion("core text area", boundingBox);
        pdfStripper.extractRegions(document.getPage(pageNum - 1));
        return pdfStripper.getTextForRegion("core text area");
      } catch (IOException e) {
        throw new IllegalArgumentException("ERROR: Issue with PDF");
      }
    } else {
      throw new IllegalArgumentException("ERROR: Invalid page number");
    }
  }

  @Override
  public void close() throws IOException {
    if (document != null) {
      document.close();
    }
  }
}
