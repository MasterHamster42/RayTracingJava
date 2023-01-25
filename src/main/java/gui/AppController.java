package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import raytacer.Engine;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class AppController implements Initializable {

    @FXML
    public Button LoadSceneButton;
    @FXML
    public TextField HeightField;
    public TextField Samples;
    public TextField Fov;
    @FXML
    private TextField WidthField;
    @FXML
    public TextField Depth;
    @FXML
    private GridPane grid;
    private final Stage stage = App.getPrimaryStage();
    private Scene scene;

    /**
     * This method is required to be called once before using the controller.
     */
    public void init() {
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        double maxHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double maxWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        System.out.println(maxHeight);
        System.out.println(maxWidth);
        stage.setMaxHeight(maxHeight);
        stage.setMaxWidth(maxWidth);
        // load default config settings
        Path resourceDirectory = Paths.get("src","main","resources","default");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
//        Engine engine = new Engine(800, 800);
//        engine.renderAndSaveScene();
    }


    @FXML
    private void runFileChooser() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setTitle("Open Config file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.'));
//            this.loadConfig(path);
        }
    }
    @FXML
    private void runRaytracing(ActionEvent event) throws IOException {
        if (!checkSettings()) return;
        RenderingController renderingController = App.getRenderingController();
        int width = Integer.parseInt(WidthField.getText());
        int height = Integer.parseInt(HeightField.getText());
        int depth = Integer.parseInt(Depth.getText());
        int samples = Samples.getCharacters().isEmpty() ? 2 : Integer.parseInt(Samples.getText());
        int fov = Integer.parseInt(Fov.getText());

        renderingController.setEngine(new Engine(width, height, depth, samples, fov));

        //creating window
        Stage renderingStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        renderingStage.setScene(renderingController.getScene());
        renderingController.setSize(width, height);
        renderingController.init();
        renderingStage.show();
    }

    private boolean checkSettings(){
        if (!Pattern.matches("^[1-9][0-9]*$", HeightField.getCharacters())) return false;
        if (!Pattern.matches("^[1-9][0-9]*$", WidthField.getCharacters())) return false;
        if (!Pattern.matches("^[1-9][0-9]*$", Depth.getCharacters())) return false;
        if (!Pattern.matches("^[1-9][0-9]*$", Samples.getCharacters()) && !Samples.getCharacters().isEmpty()) return false;
        if (!Pattern.matches("^[1-9][0-9]*$", Fov.getCharacters())) return false;
        return Integer.parseInt(Fov.getText()) <= 360;
    }

    // overrides

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // getters/setters

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene(){
        return scene;
    }

}
