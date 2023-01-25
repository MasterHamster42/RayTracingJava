package raytacer;
import org.json.JSONArray;

public class ColorRGB{
    double R, G, B;

    public ColorRGB add(ColorRGB a) {
        return new ColorRGB(this.R +a.R, this.G +a.G, this.B +a.B);
    }

    public ColorRGB add(double a){
        return new ColorRGB(this.R +a, this.G +a, this.B +a);
    }
    /**
     * mutates existing Color vector
     * @param t number by which vector will be multiplied
     * @return vector multiplied by t
     */
    public ColorRGB multiplyThis(double t){
        this.R *= t;
        this.G *= t;
        this.B *= t;
        return this;
    }

    public ColorRGB multiply(double t){
        return new ColorRGB(R *t, G *t, B *t);
    }

    public ColorRGB multiply(ColorRGB a){
        return  new ColorRGB(R*a.R, G*a.G, B*a.B);
    }

    public ColorRGB addThis(ColorRGB a){
        this.R += a.R;
        this.G += a.G;
        this.B += a.B;
        return this;
    }

    public ColorRGB sqrtThis(){
        R = Math.sqrt(R);
        G = Math.sqrt(G);
        B = Math.sqrt(B);
        return this;
    }

    public ColorRGB zeroThis(){
        R = 0;
        G = 0;
        B = 0;
        return this;
    }

    public ColorRGB clampThis(float from, float to){
        R = Math.max(Math.min(R, to), from);
        G = Math.max(Math.min(G, to), from);
        B = Math.max(Math.min(B, to), from);
        return this;
    }

    public ColorRGB multiplyThis(ColorRGB a){
        R *= a.R;
        G *= a.G;
        B *= a.B;
        return this;
    }
    public static ColorRGB toRGBVec(int rgb){
        int r, g, b;
        r = (rgb >> 16) & 255;
        g = (rgb >> 8) & 255;
        b = rgb & 255;
        return new ColorRGB(r,g,b);
    }

    public ColorRGB copy(){
        return new ColorRGB(R, G, B);
    }

    public String toString() {
        return "raytacer.Vector3{" + R +
                "," + G +
                "," + B +
                '}';
    }

    //Constructor
    public ColorRGB(double R, double G, double B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public ColorRGB(){
        R = 0;
        G = 0;
        B = 0;
    }

    public ColorRGB(JSONArray jsonArray){
        R = jsonArray.getDouble(0);
        G = jsonArray.getDouble(1);
        B = jsonArray.getDouble(2);
    }
}
