package raytacer;

import org.json.JSONArray;

import java.util.concurrent.ThreadLocalRandom;

public class Vector3{
    double x, y, z;


    /**
     * Calculates dot product between itself and vector a
     * @param a vector
     * @return dot product of itself and a
     */
    public double dot(Vector3 a){
        return this.x*a.x + this.y*a.y + this.z*a.z;
    }

    /**
     * mutates existing vector
     * @param t number by which vector will be multiplied
     * @return vector multiplied by t
     */
    public Vector3 multiplyThis(double t){
        this.x *= t;
        this.y *= t;
        this.z *= t;
        return this;
    }

    /**
     * creates copy of vector, multiplied by t
     * @param t number by which copy will be multiplied
     * @return copy multiplied by t
     */
    public Vector3 multiply(double t){
        return new Vector3(x*t, y*t, z*t);
    }

    public double norm(){
        return Math.sqrt(x*x + y*y +z*z);
    }

    public double normSquared(){
        return x*x + y*y +z*z;
    }

    /**
     * Normalizes vector, won't work on (0,0,0) vector
     * @return itself
     */
    public Vector3 normalizeThis(){
        return this.multiplyThis(1/this.norm());
    }

    public Vector3 normalize(){
        return this.copy().multiplyThis(1/this.norm());
    }

    public Vector3 addThis(Vector3 a){
        this.x += a.x;
        this.y += a.y;
        this.z += a.z;
        return this;
    }

    public Vector3 addThis(double a){
        this.x += a;
        this.y += a;
        this.z += a;
        return this;
    }

    public Vector3 add(Vector3 a){
        return new Vector3(this.x+a.x, this.y+a.y, this.z+a.z);
    }

    public Vector3 add(double a){
        return new Vector3(this.x+a, this.y+a, this.z+a);
    }

    public Vector3 subtractThis(Vector3 a){
        this.x -= a.x;
        this.y -= a.y;
        this.z -= a.z;
        return this;
    }

    public Vector3 subtract(Vector3 a){
        return new Vector3(this.x-a.x, this.y-a.y, this.z-a.z);
    }

    public static Vector3 vectorFromTo(Vector3 a, Vector3 b){
        return b.subtract(a);
    }

    public static Vector3 randomPointInUnitSphere() {
        ThreadLocalRandom rd = ThreadLocalRandom.current();
//        UniformRandomProvider rd2 = ThreadLocalRandomSource.current();

        while (true) {
            Vector3 p = new Vector3(rd.nextDouble(-1,1), rd.nextDouble(-1,1), rd.nextDouble(-1,1));
            if (p.normSquared() >= 1) continue;
            return p;
        }
    }

    public static Vector3 randomUnitVector(){
        return randomPointInUnitSphere().normalizeThis();
    }

    public Vector3 crossProduct(Vector3 a){
        double newX = y*a.z - z*a.y;
        double newY = z*a.x - x*a.z;
        double newZ = x*a.y - y*a.x;
        return new Vector3(newX, newY, newZ);
    }
    public Vector3 sqrtThis(){
        x = Math.sqrt(x);
        y = Math.sqrt(y);
        z = Math.sqrt(z);
        return this;
    }
    public Vector3 copy(){
        return new Vector3(x, y, z);
    }

    public ColorRGB castToColor(){
        return new ColorRGB(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "raytacer.Vector3{" + x +
                "," + y +
                "," + z +
                '}';
    }

    //Constructors
    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0,0,0);
    }

    public Vector3(JSONArray jsonArray){
        this.x = jsonArray.getDouble(0);
        this.y = jsonArray.getDouble(1);
        this.z = jsonArray.getDouble(2);
    }

}
