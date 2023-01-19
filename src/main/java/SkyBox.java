public class SkyBox {

    public ColorRGB returnColorAt(Vector3 origin, Vector3 direction){
        double t = 0.5*(direction.normalizeThis().y + 1.0);
        return (new ColorRGB(1.0, 1.0, 1.0)).multiplyThis((1.0-t)).addThis((new ColorRGB(0.5, 0.7, 1.0)).multiplyThis(t));
    }
}
