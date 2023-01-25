package raytacer;

import net.jafama.FastMath;

public class Sphere extends SceneObject {
    private final double radius;
    private final Vector3 center;
    @Override
    public double detectHit(Ray ray) {
        Vector3 AC = ray.origin.subtract(center);
        double a = ray.direction.dot(ray.direction);
        double bHalf = ray.direction.dot(AC);
        double c = AC.dot(AC) - radius*radius;
        double deltaSmaller = bHalf*bHalf - a*c;

        if (deltaSmaller < 0){ return -1;}

        double t_min = 0;
        double t_max = Double.MAX_VALUE;

        double deltaSmallerSqrt = Math.sqrt(deltaSmaller);
        // Find the nearest root that lies in the acceptable range.
        double root = (-bHalf - deltaSmallerSqrt) / a;
        if (root < t_min || t_max < root) {
            root = (-bHalf + deltaSmallerSqrt) / a;
            if (root < t_min || t_max < root)
                return -1;
        }
        return root;
    }

    public CollisionInformation hitInformation(Ray ray, Vector3 hitPosition){

        CollisionInformation collisionInformation = new CollisionInformation();
        collisionInformation.sceneObject = this;
        collisionInformation.point = hitPosition;

        Vector3 outwardNormal = Vector3.vectorFromTo(center, collisionInformation.point);
        collisionInformation.front_face = ray.direction.dot(outwardNormal) < 0.0;
        collisionInformation.normal = outwardNormal.multiplyThis(collisionInformation.front_face ? 1/radius: -1/radius);
        return collisionInformation;
    }

    @Override
    public ColorRGB returnColor(CollisionInformation collisionInformation) {
//        if (material.getClass().equals(Dielectric.class)){
//            return new ColorRGB(1,1,1);
//        }
        if (texture.getClass().equals(SolidColorTexture.class)){
            return ((SolidColorTexture) texture).returnColor();
        }
        //TODO calculate u,v
        Vector3 point = collisionInformation.point;
        double theta = FastMath.acos(-point.y);
        double phi = FastMath.atan2(-point.z, point.x) + FastMath.PI;

        double u = phi / (2*FastMath.PI);
        double v = theta / FastMath.PI;
        u *= texture.getWidth();
        v *= texture.getHeight();

        return texture.returnColor(u,v);
    }


    public Sphere(double radius, Vector3 center) {
        this(radius, center, new RoughMaterial());
    }

    public Sphere(double radius, Vector3 center, Material material){
        this(radius, center, material, new SolidColorTexture());
    }

    public Sphere(double radius, Vector3 center, Material material, Texture texture){
        this.radius = radius;
        this.center = center;
        this.material = material;
        this.texture = texture;
    }
}
