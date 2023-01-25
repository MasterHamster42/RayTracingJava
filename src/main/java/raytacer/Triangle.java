package raytacer;

public class Triangle extends SceneObject{
    private final Vector3 vertex1;
    private double lastU;
    private double lastV;
    private final Vector3 normal;
    private final Vector3 edge1;
    private final Vector3 edge2;
    private final double EPSILON = 0.0000001;
    @Override
    // MollerTrumbore algorithm, based on wikipedia https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
    public double detectHit(Ray ray) {
        Vector3 h = ray.direction.crossProduct(edge2);

        double a, f, u, v;

        a = edge1.dot(h);

        // This ray is parallel to this triangle.
        if (a > -EPSILON && a < EPSILON) {
            return -1;
        }
        f = 1.0 / a;
        Vector3 s = ray.origin.subtract(vertex1);
        u = f * (s.dot(h));
        if (u < 0.0 || u > 1.0) {
            return -1;
        }
        Vector3 q = s.crossProduct(edge1);
        v = f * ray.direction.dot(q);
        if (v < 0.0 || u + v > 1.0) {
            return -1;
        }
        lastU = u;
        lastV = v;

        double t = f * edge2.dot(q);
        return t > 0 ? t : -1;
    }

    @Override
    public ColorRGB returnColor(CollisionInformation collisionPoint) {
        if (texture.getClass().equals(SolidColorTexture.class)){
            return ((SolidColorTexture) texture).returnColor();
        }

        return texture.returnColor(lastU*texture.getWidth(), lastV*texture.getHeight());
    }

    @Override
    public CollisionInformation hitInformation(Ray ray, Vector3 hitPosition) {
        CollisionInformation collisionInformation = new CollisionInformation();
        collisionInformation.sceneObject = this;
        collisionInformation.point = hitPosition;

        collisionInformation.front_face = ray.direction.dot(normal) < 0.0;
        collisionInformation.normal = normal.multiply(collisionInformation.front_face ? 1 : -1);
        return collisionInformation;
    }

    public Triangle(Vector3 vertex1, Vector3 vertex2, Vector3 vertex3) {
        this(vertex1, vertex2, vertex3, new RoughMaterial(), new SolidColorTexture());
    }

    public Triangle(Vector3 vertex1, Vector3 vertex2, Vector3 vertex3, Vector3 normal){
        this(vertex1, vertex2, vertex3, normal, new RoughMaterial(), new SolidColorTexture());
    }

    public Triangle(Vector3 vertex1, Vector3 vertex2, Vector3 vertex3, Material material, Texture texture){
        this.vertex1 = vertex1;
        this.edge1 = vertex2.subtract(vertex1);
        this.edge2 = vertex3.subtract(vertex1);
        this.normal = edge1.crossProduct(edge2).normalizeThis();
        this.material = material;
        this.texture = texture;
    }

    public Triangle(Vector3 vertex1, Vector3 vertex2, Vector3 vertex3, Vector3 normal, Material material, Texture texture){
        this.vertex1 = vertex1;
        this.normal = normal.normalizeThis();
        this.edge1 = vertex2.subtract(vertex1);
        this.edge2 = vertex3.subtract(vertex1);
        this.material = material;
        this.texture = texture;
    }
}
