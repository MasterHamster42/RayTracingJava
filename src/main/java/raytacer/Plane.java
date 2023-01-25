package raytacer;

public class Plane extends SceneObject{
    private final Vector3 normal;
    private final Vector3 center;
    // basis vectors of a plane
    private final Vector3 e1;
    private final Vector3 e2;

    @Override
    public double detectHit(Ray ray) {
        double pn = normal.dot(ray.direction);
        if (pn == 0){
            // -1 means no collision
            return -1;
        }
        double t = center.subtract(ray.origin).dot(normal)/pn;
        // collision behind the ray
        if (t < 0){
            return -1;
        }
        return t;
    }

    @Override
    public ColorRGB returnColor(CollisionInformation collisionPoint) {
        if (material.getClass().equals(Dielectric.class)){
            return new ColorRGB(1,1,1);
        }
        else if (texture.getClass().equals(SolidColorTexture.class)){
            return ((SolidColorTexture) texture).returnColor();
        }
        double u = e1.dot(collisionPoint.point.subtract(center));
        double v = e2.dot(collisionPoint.point.subtract(center));
        return texture.returnColor(u,v);
    }

    @Override
    public CollisionInformation hitInformation(Ray ray, Vector3 hitPosition) {
        CollisionInformation collisionInformation = new CollisionInformation();
        collisionInformation.sceneObject = this;
        collisionInformation.point = hitPosition;

        // plane doesn't distinguish faces
        collisionInformation.front_face = normal.dot(ray.direction) < 0;
        collisionInformation.normal = normal.multiply(collisionInformation.front_face ? 1 : -1);
        return collisionInformation;
    }

    public Plane(Vector3 normal, Vector3 center, Material material) {
        this(normal, center, material, new SolidColorTexture());
    }

    public Plane(Vector3 normal, Vector3 center, Material material, Texture texture) {
        this.normal = normal.normalizeThis();
        this.center = center;
        this.material = material;
        this.texture = texture;
        this.e1 = normal.crossProduct(center.add(1)).normalizeThis();
        this.e2 = normal.crossProduct(e1).normalizeThis();
    }
}
