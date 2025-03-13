package project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    public static void createPDF(List<String> translatedPages, String outputPath) throws IOException {
        PDDocument document = new PDDocument();

        for (String pageText : translatedPages) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(25, 700);

            String[] lines = pageText.split("\n");
            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -15); // 줄 간격
            }
            contentStream.endText();
            contentStream.close();
        }

        document.save(outputPath);
        document.close();
    }
}
