package model;

import javafx.fxml.Initializable;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class EditableImage extends ImageView implements Initializable {
    public void RGBtoGrey(){
        Image image = getImage();
        PixelReader preader = image.getPixelReader();
        WritableImage wimage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pwriter = wimage.getPixelWriter();
        int count = 0;
        for (int i = 0; i < (int) image.getHeight(); i++) {
            for (int j = 0; j < (int) image.getWidth(); j++) {
                count += 1;
                Color col = preader.getColor(j, i);
                //Reading each pixel and converting it into gray scale
                pwriter.setColor(j, i, new Color((col.getRed() * 0.3 + col.getGreen() * 0.59 + col.getBlue() * 0.11), (col.getRed() * 0.3 + col.getGreen() * 0.59 + col.getBlue() * 0.11), (col.getRed() * 0.3 + col.getGreen() * 0.59 + col.getBlue() * 0.11), 1.0));
            }
        }
        setImage(wimage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
