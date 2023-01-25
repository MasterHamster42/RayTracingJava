import gui.App;
import javafx.application.Application;

import static utils.PrettyPrint.p;

public class Main {

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        Application.launch(App.class);
//        Application.main("Application");
//        raytacer.Engine engine = new raytacer.Engine(new Application(), 1920, 1080);
//        engine.renderAndSaveScene();
//        Application application = new Application();
//        application.draw();
        p("Execution time:%s", System.currentTimeMillis()-start);
    }
}
