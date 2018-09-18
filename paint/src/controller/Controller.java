package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.concurrent.WorkerStateEvent;
import model.ImageEditor;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Controller {
    Image original;
    ImageEditor imgEditor;

    @FXML BorderPane scenePane;
    @FXML AnchorPane imgPane;
    @FXML ImageView imgView;
    @FXML ChoiceBox choice_color;
    @FXML ProgressBar pbar;

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
            original = img;
        }
    }

    @FXML
    public void greyscaleRender(){
        imgEditor = new ImageEditor(original);

        pbar.progressProperty().unbind();
        pbar.progressProperty().bind(imgEditor.progressProperty());
        pbar.setVisible(true);
        //Event fired when the service succeeded successfully!!!
        imgEditor.setOnSucceeded((WorkerStateEvent we) -> {
            imgView.setImage(imgEditor.getValue());
            pbar.setVisible(false);
        });
        imgEditor.restart();
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

    public void setColorOptions(){
        ObservableList<String> colors //
                = FXCollections.observableArrayList("red", "green", "blue");
        choice_color.setItems(colors);
        choice_color.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String oldValue, String newValue) {
                if (newValue != null) {
                    imgEditor = new ImageEditor(original, newValue);
                    pbar.progressProperty().unbind();
                    pbar.progressProperty().bind(imgEditor.progressProperty());
                    pbar.setVisible(true);
                    //Event fired when the service succeeded successfully!!!
                    imgEditor.setOnSucceeded((WorkerStateEvent we) -> {
                        imgView.setImage(imgEditor.getValue());
                        pbar.setVisible(false);
                    });
                    imgEditor.restart();
                }
            }
        });
    }

    public void saveImage(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("save");
        fileChooser.setTitle("Save picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imgView.getImage(), null), "png", file);
            } catch (IOException ex) {
                System.out.println("aucun fichier choisi");
            }
        }
    }
}
