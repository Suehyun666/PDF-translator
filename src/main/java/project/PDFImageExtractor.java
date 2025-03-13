package project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFImageExtractor {
    public static BufferedImage convertPdfPageToImage(File pdfFile, int pageIndex) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300);  // 300 DPI로 높은 해상도

        document.close();
        return image;
    }
}
