package raytacer;

public class SolidColorTexture extends Texture{
    ColorRGB colorRGB;
    @Override
    public ColorRGB returnColor(double u, double v) {
        return colorRGB;
    }

    public ColorRGB returnColor(){
        return colorRGB;
    }

    //constructor

    public SolidColorTexture(ColorRGB colorRGB){
        this.colorRGB = colorRGB;
    }

    public SolidColorTexture(){
        this.colorRGB = new ColorRGB(0.5,0.5,0.5);
    }
}
