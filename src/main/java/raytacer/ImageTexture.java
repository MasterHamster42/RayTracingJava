package raytacer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTexture extends Texture{
    private BufferedImage texture;
    private double scale = 1;
    @Override
    public ColorRGB returnColor(double u, double v) {
        u /= scale;
        v /= scale;
        //wrapping u, v
        if( u >= texture.getWidth()){
            u = u % texture.getWidth();
        }
        else if (u < 0){
            u = u % texture.getWidth() + texture.getWidth();
        }
        if( v >= texture.getHeight()){
            v = v % texture.getHeight();
        }
        else if (v < 0){
            v = v % texture.getHeight() + texture.getHeight();
        }

        return ColorRGB.toRGBVec(texture.getRGB((int) u, (int)v)).multiplyThis(1.0/255);
    }

    public int getWidth(){
        return texture.getWidth();
    }

    public int getHeight(){
        return texture.getHeight();
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setScale(double scale){
        this.scale = scale;
    }

    //constructor
    public ImageTexture(String path){
        try {
            texture = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
