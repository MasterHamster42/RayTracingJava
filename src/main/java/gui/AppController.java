package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import raytacer.Engine;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private raytacer.Scene rayTracingScene;

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

    }


    @FXML
    private void runFileChooser() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setTitle("Open Config file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.'));
            raytacer.Scene scene = new raytacer.Scene();
            scene.loadConfig(path);
            scene.getSkyBox().loadSkyBox("src/main/resources/skybox_desert.png");
            rayTracingScene = scene;
        }
    }
    @FXML
    private void runRaytracing(ActionEvent event) {
        if (!checkSettings()) return;
//        if(scene == null) return;
        RenderingController renderingController = App.getRenderingController();
        int width = Integer.parseInt(WidthField.getText());
        int height = Integer.parseInt(HeightField.getText());
        int depth = Integer.parseInt(Depth.getText());
        int samples = Samples.getCharacters().isEmpty() ? 2 : Integer.parseInt(Samples.getText());
        int fov = Integer.parseInt(Fov.getText());
        Engine engine = new Engine(width, height, depth, samples, fov);
        if(rayTracingScene != null) engine.setScene(rayTracingScene);
        renderingController.setEngine(engine);

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
