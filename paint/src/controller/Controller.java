package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    ImageView imgView;

    @FXML
    public void openImage(){
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File f = fileChooser.showOpenDialog(new Stage());
        if (f != null) {
            Image img = new Image(f.toURI().toString());
            imgView.setImage(img);
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
}
