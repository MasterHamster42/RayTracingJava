package raytacer;

public abstract class Texture {
    public abstract ColorRGB returnColor(double u, double v);

    public int getWidth(){
        return 1;
    }
    public int getHeight(){
        return 1;
    }
}
