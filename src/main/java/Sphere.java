public class Sphere extends SceneObject {
    private final double radius;
    private final Vector3 center;
    @Override
    public boolean detectHit(Vector3 origin, Vector3 direction, CollisionInformation collisionInformation) {
        Vector3 AC = origin.subtract(center);
        double a = direction.dot(direction);
        double bHalf = direction.dot(AC);
        double c = AC.dot(AC) - radius*radius;
        double deltaSmaller = bHalf*bHalf - a*c;
//        if (deltaSmaller < - Epsilon){ return false;}
        if (deltaSmaller < 0){ return false;}

        double t_min = Epsilon;
        double t_max = collisionInformation.t;

        double deltaSmallerSqrt = Math.sqrt(deltaSmaller);
        // Find the nearest root that lies in the acceptable range.
        double root = (-bHalf - deltaSmallerSqrt) / a;
        if (root < t_min || t_max < root) {
            root = (-bHalf + deltaSmallerSqrt) / a;
            if (root < t_min || t_max < root)
                return false;
        }

        collisionInformation.t = root;
        collisionInformation.sceneObject = this;
        collisionInformation.point = origin.add(direction.multiply(root));
        Vector3 outwardNormal = Vector3.vectorFromTo(center, collisionInformation.point);

        collisionInformation.front_face = direction.dot(outwardNormal) < 0.0;
        collisionInformation.normal = outwardNormal.multiplyThis(collisionInformation.front_face ? 1/radius: -1/radius);
//        // choosing normal vector that points in opposite direction to our ray
//        if (direction.dot(outwardNormal) > 0.0) {
//            // ray is inside the sphere
//            collisionInformation.normal = outwardNormal.multiplyThis(-1/radius);
//            collisionInformation.front_face = false;
//        } else {
//            // ray is outside the sphere
//            collisionInformation.normal = outwardNormal.multiplyThis(1/radius);
//            collisionInformation.front_face = true;
//        }
        return true;
    }

    @Override
    public ColorRGB returnColor(CollisionInformation collisionInformation) {
        return (collisionInformation.normal.add(1)).multiplyThis(0.5).castToColor();
//        return new Vector3(1,0,0);
    }

    public Sphere(double radius, Vector3 center) {
        this.radius = radius;
        this.center = center;
    }
}
