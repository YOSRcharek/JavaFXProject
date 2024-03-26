// Model Package
package application.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PDFViewer extends StackPane {

    public PDFViewer(Blob document) {
        try {
            InputStream inputStream = document.getBinaryStream();
            PDDocument pdfDocument = PDDocument.load(inputStream);
            
            PDFRenderer renderer = new PDFRenderer(pdfDocument);

            Image image = convertToImage(renderer.renderImage(0));

            ImageView imageView = new ImageView(image);
            getChildren().add(imageView);

            pdfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image convertToImage(java.awt.image.BufferedImage bufferedImage) throws IOException {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
