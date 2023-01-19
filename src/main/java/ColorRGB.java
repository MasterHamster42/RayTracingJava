public class ColorRGB{
    double x, y, z;

    public ColorRGB add(ColorRGB a) {
        return new ColorRGB(this.x+a.x, this.y+a.y, this.z+a.z);
    }

    public ColorRGB add(double a){
        return new ColorRGB(this.x+a, this.y+a, this.z+a);
    }
    /**
     * mutates existing Color vector
     * @param t number by which vector will be multiplied
     * @return vector multiplied by t
     */
    public ColorRGB multiplyThis(double t){
        this.x *= t;
        this.y *= t;
        this.z *= t;
        return this;
    }

    public ColorRGB multiply(double t){
        return new ColorRGB(x*t, y*t, z*t);
    }

    public ColorRGB addThis(ColorRGB a){
        this.x += a.x;
        this.y += a.y;
        this.z += a.z;
        return this;
    }

    public ColorRGB sqrtThis(){
        x = Math.sqrt(x);
        y = Math.sqrt(y);
        z = Math.sqrt(z);
        return this;
    }

    //Constructor
    public ColorRGB(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
