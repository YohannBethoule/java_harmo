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
        fileChooser.setInitialFileName("save");
        fileChooser.setTitle("Save picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imgView.getImage(), null), "jpg", file);
            } catch (IOException ex) {
                System.out.println("aucun fichier choisi");
            }
        }
    }

   /* public void saveImage(ZoneDessin ptD){
        JFileChooser jSauverIm = new JFileChooser();
        jSauverIm.showSaveDialog(this);
        File f = jSauverIm.getSelectedFile();
        String s = jSauverIm.getName(f);
        BufferedImage image = new BufferedImage(ptD.getHeight(), ptD.getWidth(), BufferedImage.TYPE_INT_ARGB);
        System.out.println(ptD.getHeight()); System.out.println(ptD.getWidth());
        FileImageOutputStream output = new FileImageOutputStream(f);
        ImageIO.write(image, "jpg", output);
    }*/

}
