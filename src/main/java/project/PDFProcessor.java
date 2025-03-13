package project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class PDFProcessor {
    public static String extractText(File pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();

        // 페이지별로 텍스트 추출
        StringBuilder text = new StringBuilder();
        for (int page = 0; page < document.getNumberOfPages(); page++) {
            stripper.setStartPage(page + 1);
            stripper.setEndPage(page + 1);
            text.append(stripper.getText(document));
        }
        document.close();
        return text.toString();
    }
}
