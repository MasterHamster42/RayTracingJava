package raytacer;
import net.jafama.FastMath;

import java.util.concurrent.ThreadLocalRandom;


public class Dielectric extends Material{
    private final double refraction;


    @Override
    public Vector3 reflect(Vector3 direction, CollisionInformation collisionInformation) {
        double refraction_ratio = collisionInformation.front_face ? (1.0/refraction) : refraction;
        Vector3 normal = collisionInformation.normal;

        double cos_theta = FastMath.min(normal.dot(direction.multiply(-1)), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta*cos_theta);

        boolean cannot_refract = refraction_ratio * sin_theta > 1.0;
        Vector3 newDirection;
        ThreadLocalRandom rd = ThreadLocalRandom.current();

        if (cannot_refract || reflectance(cos_theta, refraction_ratio) > rd.nextDouble()){
            newDirection = reflectIdeal(direction, normal);
        }
        else {
            newDirection = refract(direction, normal, refraction_ratio, cos_theta);
        }

        return newDirection.normalizeThis();
    }

    private Vector3 reflectIdeal(Vector3 direction, Vector3 normal){
        return direction.subtract(normal.multiply(2*normal.dot(direction)));
    }

    private Vector3 refract(Vector3 direction, Vector3 normal, double refraction_ratio, double cos_theta){
        Vector3 perpendicularComponent =  direction.add(normal.multiply(cos_theta)).multiplyThis(refraction_ratio);
        Vector3 parallelComponent = normal.multiply(-Math.sqrt(FastMath.abs(1.0 - perpendicularComponent.normSquared())));
        return perpendicularComponent.addThis(parallelComponent);
    }

    private double reflectance(double cosine, double refractionRatio){
        double reflectance = (1-refractionRatio) / (1+refractionRatio);
        reflectance = reflectance*reflectance;
        return reflectance + (1-reflectance)*FastMath.pow((1 - cosine),5);
    }

    public Dielectric(double refraction, ColorRGB emissive){
        this.refraction = refraction;
        this.emmissive = emissive;
    }
}
