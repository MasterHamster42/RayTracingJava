package raytacer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;
import static utils.PrettyPrint.p;

public class Engine {
    Camera camera;
    Scene scene = new Scene();
    double[][] imgAccumulated;
    int framesAccumulated = 1;
//    RenderingController window;



    public Engine(int width, int height, int samples, int depth, int fov){

        this.camera = new Camera(width, height, new Vector3(0,0,0), new Vector3(0,0,-1), samples, depth, fov, scene);
        imgAccumulated = new double[width*height][3];

        //Textures
        Texture texture1 = new SolidColorTexture(new ColorRGB(0.5,0.5,0.5));
        Texture texture2 = new SolidColorTexture(new ColorRGB(0.1,.80,0.1));
        Texture texture4 = new SolidColorTexture(new ColorRGB(0.1,.1,0.1));
        ImageTexture texture3 = new ImageTexture("src/main/resources/texture-background.jpg");
        texture3.setScale(1);

        //Adding materials
        RoughMaterial material1 = new RoughMaterial(0.1, new ColorRGB());
        RoughMaterial material2 =  new RoughMaterial(1, new ColorRGB(0.1,.80,0.1));
        RoughMaterial material3 =  new RoughMaterial(0.1, new ColorRGB());
        Dielectric material4 = new Dielectric(1.5, new ColorRGB());

        //Adding objects to scene
        scene.addObject(new Sphere(0.5, new Vector3(0.5,0,-2),material2, texture2));
        scene.addObject(new Sphere(100, new Vector3(0,-100.5,-2), material3, texture2));
        scene.addObject(new Sphere(0.5, new Vector3(-0.5,0,-2),material1, texture3));
        scene.addObject(new Plane(new Vector3(1, 0, 0), new Vector3(-2, 0, -10), material1, texture1));
        scene.addObject(new Triangle(new Vector3(0,0.8,-2), new Vector3(-1,1,-2), new Vector3(-2,2,-2), material1, texture3) );


        //Loading all added objects to scene
        scene.loadScene();

        scene.getSkyBox().loadSkyBox("src/main/resources/skybox_desert.png");
//        scene.getSkyBox().loadSkyBox(new ColorRGB());
    }

    public void renderAndSaveScene(){
        BufferedImage renderFrame = camera.renderScene();

        try ( FileOutputStream fileOutputStream = new FileOutputStream("Renders/image"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) +".png")) {
            ImageIO.write(renderFrame, "png", fileOutputStream);
        }
        catch (Exception e) {
            p("Error: %s", e.getMessage());
        }
    }

    public void returnFrame(int[] pixels){

        camera.renderFrameParallel(imgAccumulated,pixels, framesAccumulated);

        for (int global_id = 0; global_id < imgAccumulated.length; global_id++) {

            int ir = (int) Math.floor(imgAccumulated[global_id][0] *255);
            int ig = (int) Math.floor(imgAccumulated[global_id][1]  *255);
            int ib = (int) Math.floor(imgAccumulated[global_id][2]  *255);
            int p = (255<<24) | (ir<<16) | (ig<<8) | ib;

            pixels[global_id] = p;

            }
      framesAccumulated += 1;

    }

//    public void renderFrame(){
//
//        camera.renderFrameParallel(imgAccumulated, framesAccumulated);
//
////        BufferedImage bufferedImage = new BufferedImage(window.width, window.height, BufferedImage.TYPE_INT_RGB);
////        p("Frames accumulated:%s\n", framesAccumulated);
////        if (framesAccumulated == 500){
//            for (int global_id = 0; global_id < imgAccumulated.length; global_id++) {
//                    int i = window.height - global_id/(window.width) -1;
//                    int j = global_id%(window.width);
////                    p("rgb: %s", imgAccumulated[i]);
//                    ColorRGB pixelColor = imgAccumulated[global_id].multiply(1.0/framesAccumulated).sqrtThis();
//                    int ir = (int) Math.floor(pixelColor.R *255);
//                    int ig = (int) Math.floor(pixelColor.G *255);
//                    int ib = (int) Math.floor(pixelColor.B *255);
//
////                    int p = (1<<24) | (ir<<16) | (ig<<8) | ib;
////                    bufferedImage.setRGB(j,window.height-i-1,p);
//                    window.pixels[global_id] = window.color(ir,ig,ib);
//            }
//      framesAccumulated += 1;
////            try ( FileOutputStream fileOutputStream = new FileOutputStream("Renders/image"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) +".png")) {
////                ImageIO.write(bufferedImage, "png", fileOutputStream);
////            }
////            catch (Exception e) {
////                p("Error: %s", e.getMessage());
////            }
////        }
//    }

    public Camera getCamera(){
        return camera;
    }

    public Scene getScene(){
        return  scene;
    }

    public void moveCamera(Vector3 origin, Vector3 lookingAt){
        for (int i = 0; i < imgAccumulated.length; i++) {
            imgAccumulated[i][0] = 0;
            imgAccumulated[i][1] = 0;
            imgAccumulated[i][2] = 0;
        }
    }

}
