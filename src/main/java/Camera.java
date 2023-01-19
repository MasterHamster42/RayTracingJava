import java.awt.image.BufferedImage;
import static utils.PrettyPrint.p;

public class Camera {
    private final int width;
    private final int height;
    private final int samples;
    private final int depth;
    private final StringBuilder frame;
    private final BufferedImage bufferedImage;
    private Vector3 origin;
    private Vector3 leftLowerCorner;
    private final Vector3 horizontal;
    private final Vector3 vertical;


    public BufferedImage renderFrame(){
        for (int i = height-1; i > -1; i--) {
            p("Rows left to render: %s \n", i+1);
            for (int j = 0; j < width; j++) {
                Ray ray = new Ray(origin, depth);
                ColorRGB pixelColor = new ColorRGB(0,0,0);
                //Sending rays with small random offset
                for (int k = 0; k < samples; k++) {
                    double v = (i + Math.random()) / (height-1.0);
                    double u = (j + Math.random()) / (width-1.0);
                    pixelColor.addThis(ray.shootRayAt(Vector3.vectorFromTo(origin, leftLowerCorner.add(horizontal.multiply(u)).add(vertical.multiply(v)).subtractThis(origin))));
                }
                //Averaging colour from all samples
                pixelColor.multiplyThis(1.0/samples).sqrtThis();
                //Square root is gamma correction for gamma=2
                int ir = (int) Math.floor(pixelColor.x*255);
                int ig = (int) Math.floor(pixelColor.y*255);
                int ib = (int) Math.floor(pixelColor.z*255);

                int p = (1<<24) | (ir<<16) | (ig<<8) | ib;
                bufferedImage.setRGB(j,height-i-1,p);
            }
        }
        return bufferedImage;
    }

    public void setOrigin(Vector3 newOrigin){
        this.origin = newOrigin;
        Vector3 horizontal = new Vector3(width, 0, 0);
        Vector3 vertical = new Vector3(0, height, 0);
        leftLowerCorner = origin.subtract(horizontal.multiplyThis(0.5)).subtractThis(vertical.multiplyThis(0.5)).subtractThis(new Vector3(0,0,1));
    }

    // Constructor

    public Camera(int width, int height, Vector3 origin, Vector3 screenCenter, int samples, int depth){
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

        frame = new StringBuilder("P3\n" + width + " " + height + "\n255\n");
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

}
