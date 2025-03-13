package project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;

public class PDFToTextWithOCR {

    public static String extractFirstPageText(File pdfFile) throws Exception {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(1); // 첫 번째 페이지만 추출
            stripper.setEndPage(1);
            return stripper.getText(document);
        }
    }
}

