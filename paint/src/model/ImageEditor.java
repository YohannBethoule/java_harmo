package model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageEditor extends Service<Image> {
    private final Image image;
    private final String color;

    public ImageEditor(Image i) {
        super();
        image = i;
        color = "grey";
    }

    public ImageEditor(Image i, String c) {
        super();
        image = i;
        color = c;
    }

    @Override
    protected Task<Image> createTask() {
        return new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                PixelReader preader = image.getPixelReader();
                WritableImage wimage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
                PixelWriter pwriter = wimage.getPixelWriter();
                int count = 0;
                switch(color){
                    case "red":
                        for (int i = 0; i < (int) image.getHeight(); i++) {
                            for (int j = 0; j < (int) image.getWidth(); j++) {
                                count += 1;
                                Color col = preader.getColor(j, i);
                                pwriter.setColor(j, i, new Color(col.getRed(), 0, 0, 1.0));
                                updateProgress(count, image.getHeight()*image.getWidth());
                            }
                        }
                        break;
                    case "green":
                        for (int i = 0; i < (int) image.getHeight(); i++) {
                            for (int j = 0; j < (int) image.getWidth(); j++) {
                                count += 1;
                                Color col = preader.getColor(j, i);
                                pwriter.setColor(j, i, new Color(0, col.getGreen(), 0, 1.0));
                                updateProgress(count, image.getHeight()*image.getWidth());
                            }
                        }
                        break;
                    case "blue":
                        for (int i = 0; i < (int) image.getHeight(); i++) {
                            for (int j = 0; j < (int) image.getWidth(); j++) {
                                count += 1;
                                Color col = preader.getColor(j, i);
                                pwriter.setColor(j, i, new Color(0, 0, col.getBlue(), 1.0));
                                updateProgress(count, image.getHeight()*image.getWidth());
                            }
                        }
                        break;
                    case "grey":
                        for (int i = 0; i < (int) image.getHeight(); i++) {
                            for (int j = 0; j < (int) image.getWidth(); j++) {
                                count += 1;
                                Color col = preader.getColor(j, i);
                                pwriter.setColor(j, i, new Color((col.getRed() * 0.3 + col.getGreen() * 0.59 + col.getBlue() * 0.11), (col.getRed() * 0.3 + col.getGreen() * 0.59 + col.getBlue() * 0.11), (col.getRed() * 0.3 + col.getGreen() * 0.59 + col.getBlue() * 0.11), 1.0));
                                updateProgress(count, image.getHeight()*image.getWidth());
                            }
                        }
                        break;
                }
                return wimage;
            }
        };
    }

}
