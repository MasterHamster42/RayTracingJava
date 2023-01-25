package raytacer;

import gui.RenderingController;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Camera {
    private final int width;
    private final int height;
    private final int samples;
    private final int depth;
    private int fov = 60;
    private final BufferedImage bufferedImage;
    private final Scene scene;
//    private final RenderingController window;
    private Vector3 origin;
    private Vector3 leftLowerCorner;
    private final Vector3 horizontal;
    private final Vector3 vertical;


    public BufferedImage renderFrame(){
        for (int i = height-1; i > -1; i--) {
//            p("Rows left to render: %s \n", i+1);
            for (int j = 0; j < width; j++) {
                RayGeneration rayGeneration = new RayGeneration(depth, scene);
                ColorRGB pixelColor = new ColorRGB(0,0,0);
                //Sending rays with small random offset
                double v = (i + Math.random()) / (height-1.0);
                double u = (j + Math.random()) / (width-1.0);
                Ray ray = new Ray(origin, Vector3.vectorFromTo(origin, leftLowerCorner.add(horizontal.multiply(u)).add(vertical.multiply(v)).subtractThis(origin)));
                pixelColor.addThis(rayGeneration.shootRayAt(ray));

                //Square root is gamma correction for gamma=2
                pixelColor.sqrtThis();
                int ir = (int) Math.floor(pixelColor.R *255);
                int ig = (int) Math.floor(pixelColor.G *255);
                int ib = (int) Math.floor(pixelColor.B *255);

                int p = (1<<24) | (ir<<16) | (ig<<8) | ib;
                bufferedImage.setRGB(j,height-i-1,p);
            }
        }
        return bufferedImage;
    }

    public void renderFrameParallel(double[][] imageAccumulated, int[] img, int framesAccumulated){
        final int[] max = {0};
        IntStream.range(0,width*height).parallel().forEach(global_id -> {

            ThreadLocalRandom rd = ThreadLocalRandom.current();
            int i = height - global_id/(width) -1;
            int j = global_id%(width);
            RayGeneration rayGeneration = new RayGeneration(depth, scene);
            ColorRGB pixelColor = new ColorRGB(0,0,0);

            //Sending rays with small random offset
            double v = (i + rd.nextDouble()) / (height-1.0);
            double u = (j + rd.nextDouble()) / (width-1.0);
            Ray ray = new Ray(origin, Vector3.vectorFromTo(origin, leftLowerCorner.add(horizontal.multiply(u)).add(vertical.multiply(v))).normalizeThis());
            pixelColor.addThis(rayGeneration.shootRayAt(ray));

            pixelColor.sqrtThis();
            pixelColor.clampThis(0,1);
            double[] p = {pixelColor.R, pixelColor.G, pixelColor.B};

            for (int k = 0; k < 3; k++) {
                double avg = imageAccumulated[global_id][k];
                imageAccumulated[global_id][k] = avg + (p[k]-avg)/framesAccumulated;
            }
        });
    }

    public BufferedImage renderScene() {
        final int[] img = new int[width*height];
        IntStream.range(0,width*height).parallel().forEach(global_id -> {
            ThreadLocalRandom rd = ThreadLocalRandom.current();
            int i = height - global_id/(width) -1;
            int j = global_id%(width);

            RayGeneration rayGeneration = new RayGeneration(depth, scene);
            ColorRGB pixelColor = new ColorRGB(0,0,0);
            //Sending rays with small random offset
            for (int k = 0; k < samples; k++) {
                double v = (i + rd.nextDouble()) / (height-1.0);
                double u = (j + rd.nextDouble()) / (width-1.0);
                Ray ray = new Ray(origin, Vector3.vectorFromTo(origin, leftLowerCorner.add(horizontal.multiply(u)).add(vertical.multiply(v))));
                pixelColor.addThis(rayGeneration.shootRayAt(ray));
            }
            //Averaging colour from all samples
            pixelColor.multiplyThis(1.0/samples).sqrtThis();
            //Square root is gamma correction for gamma=2
            int ir = (int) Math.floor(pixelColor.R *255);
            int ig = (int) Math.floor(pixelColor.G *255);
            int ib = (int) Math.floor(pixelColor.B *255);

            int p = (1<<24) | (ir<<16) | (ig<<8) | ib;
            bufferedImage.setRGB(j,height-i-1,p);
        });

        return bufferedImage;

    }

    public void setOrigin(Vector3 newOrigin){
        this.origin = newOrigin;
        Vector3 horizontal = new Vector3(width, 0, 0);
        Vector3 vertical = new Vector3(0, height, 0);
        leftLowerCorner = origin.subtract(horizontal.multiplyThis(0.5)).subtractThis(vertical.multiplyThis(0.5)).subtractThis(new Vector3(0,0,1));
    }

    // Constructor

    public Camera(int width, int height, Vector3 origin, Vector3 screenCenter, int samples, int depth, Scene scene){
        this.scene = scene;
//        this.window = window;
        this.samples = samples;
        this.depth = depth;
        this.origin = origin;
        this.width = width;
        this.height = height;

        // temporary
        double viewPortWidth = 2;
        double viewPortHeight = (height*1.0)/width*viewPortWidth;
        horizontal = new Vector3(viewPortWidth, 0, 0);
        vertical = new Vector3(0, viewPortHeight, 0);
        leftLowerCorner = origin.subtract(horizontal.multiply(0.5)).subtractThis(vertical.multiply(0.5)).subtractThis(new Vector3(0,0,1));

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public Camera(int width, int height, Vector3 origin, Vector3 lookAt, int samples, int depth, int fov, Scene scene){
        this.scene = scene;
//        this.window = window;
        this.samples = samples;
        this.depth = depth;
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.fov = fov;

        double theta = Math.toRadians(fov);
        double h =Math.tan(theta/2);
        double viewPortHeight = 2 * h;
        double viewPortWidth = ((width*1.0)/height)*viewPortHeight;
//        double viewPortHeight = (height*1.0)/width*viewPortWidth;

        Vector3 w = origin.subtract(lookAt).normalizeThis();
        Vector3 u = new Vector3(0, 1, 0).crossProduct(w).normalizeThis();
        Vector3 v = w.crossProduct(u);

        horizontal = u.multiplyThis(viewPortWidth);
        vertical =  v.multiplyThis(viewPortHeight);
        leftLowerCorner = origin.subtract(horizontal.multiply(0.5)).subtractThis(vertical.multiply(0.5)).subtractThis(w);

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

}
