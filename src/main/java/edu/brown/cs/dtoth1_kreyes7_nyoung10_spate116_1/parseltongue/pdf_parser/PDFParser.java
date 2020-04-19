package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredTextEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PDFParser {
  public static void getSnippets(String path) {
    final float MARGIN_WIDTH_RATIO = (float) (1.0 / 8.5);
    final float MARGIN_HT_RATIO = (float) (1.25 / 11.0);

    try (PdfReader reader = new PdfReader(path)) {
      PdfDocument doc = new PdfDocument(reader);
      PrintWriter out = new PrintWriter(new FileOutputStream(new File("data/random.txt")));
      PdfPage currentPage;
      Rectangle pageBoundingBox, textBoundingBox;
      float lowerLeftX, lowerLeftY, wid, ht;
      for (int i = 1; i <= doc.getNumberOfPages(); i++) {
        currentPage = doc.getPage(i);
        pageBoundingBox = currentPage.getMediaBox();
        lowerLeftX = pageBoundingBox.getLeft() + (MARGIN_WIDTH_RATIO * pageBoundingBox.getWidth());
        lowerLeftY = pageBoundingBox.getBottom() + (MARGIN_HT_RATIO * pageBoundingBox.getHeight());
        wid = pageBoundingBox.getWidth() - (2 * MARGIN_WIDTH_RATIO * pageBoundingBox.getWidth());
        ht = pageBoundingBox.getHeight() - (2 * MARGIN_HT_RATIO * pageBoundingBox.getWidth());
        textBoundingBox = new Rectangle(lowerLeftX, lowerLeftY, wid, ht);
        TextRegionEventFilter boundingFilter = new TextRegionEventFilter(textBoundingBox);
        ITextExtractionStrategy strat =
            new FilteredTextEventListener(new LocationTextExtractionStrategy(), boundingFilter);
        out.println(
            PdfTextExtractor.getTextFromPage(currentPage, strat));
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file path");
    }
  }

  public static void main(String[] args) throws IOException {
//    getSnippets("data/BP-Event-in-the-Mediterranean.pdf");
    //Loading an existing document
    File file = new File("data/BP-Event-in-the-Mediterranean.pdf");
    PDDocument document = PDDocument.load(file);

    //Instantiate PDFTextStripper class
    PDFTextStripper pdfStripper = new PDFTextStripper();

    //Retrieving text from PDF document
    String text = pdfStripper.getText(document);
    System.out.println(text);

    //Closing the document
    document.close();
  }
}
