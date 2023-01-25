package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class App extends Application {

    private static RenderingController renderingController;
    private static AppController appController;
    private static Stage primaryStage;


    // overrides

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.primaryStage = primaryStage;

        //rendering window setup
        FXMLLoader renderingLoader = new FXMLLoader(getClass().getResource("/Scene2.fxml"));
        Parent newSceneParent = renderingLoader.load();
        Scene scene1 = new Scene(newSceneParent);

        renderingController = renderingLoader.getController();
        renderingController.setScene(scene1);

        // menu window setup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/App.fxml"));

        Parent root = loader.load();
        Scene scene2 = new Scene(root);
        primaryStage.setTitle("Ray tracing menu");
        primaryStage.setScene(scene2);

        appController = loader.getController();
        appController.setScene(scene2);
        appController.init();

        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    // getters

    public static RenderingController getRenderingController() {
        return renderingController;
    }

    public static AppController getAppController() {
        return appController;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}