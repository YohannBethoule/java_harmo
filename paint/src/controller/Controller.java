package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.EditableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML BorderPane scenePane;
    @FXML AnchorPane imgPane;
    @FXML EditableImage imgView;

    @FXML
    public void openImage(){
        imgView.fitWidthProperty().bind(imgPane.widthProperty());
        imgView.fitHeightProperty().bind(imgPane.heightProperty());
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File f = fileChooser.showOpenDialog(new Stage());
        if (f != null) {
            Image img = new Image(f.toURI().toString());
            imgView.setImage(img);
        }
    }

    @FXML
    public void greyscaleRender(){
        imgView.RGBtoGrey();
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

    public void saveImage(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.setTitle("Save picture");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imgView.getImage(), null), ".png", file);
            } catch (IOException ex) {
                System.out.println("aucun fichier choisi");
            }
        }
    }
}
