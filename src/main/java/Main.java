import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.PrettyPrint.p;

public class Main {

    public static void main(String[] args){
        long start = System.currentTimeMillis();

        Camera camera = new Camera(1920, 1080, new Vector3(0,0,0), new Vector3(0,0,-1), 100, 50);
//        Scene.addSkyBox(new SkyBox());

        //Adding objects to scene
        Scene.addObject(new Sphere(0.5, new Vector3(0,0,-2)));
        Scene.addObject(new Sphere(100, new Vector3(0,-100.5,-2)));
        Scene.addObject(new Sphere(0.5, new Vector3(-0.25,0,-2)));

        //Loading all added objects to scene
        Scene.loadScene();
        //rendering Scene
        BufferedImage renderFrame = camera.renderFrame();

        try ( FileOutputStream fileOutputStream = new FileOutputStream("Renders/image"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) +".png")) {

//            byte[] arr = renderFrame.toString().getBytes();
//            fileOutputStream.write(arr);
            ImageIO.write(renderFrame, "png", fileOutputStream);
        }
        catch (Exception e) {
            p("Error: %s", e.getMessage());
        }
        p("Execution time:%s", System.currentTimeMillis()-start);
    }
}
