public class CollisionInformation{
    Vector3 point;
    Vector3 normal;
    double t;
    boolean front_face;
    SceneObject sceneObject;

    public CollisionInformation(Vector3 point, Vector3 normal, double t, boolean front_face) {
        this.point = point;
        this.normal = normal;
        this.t = t;
        this.front_face = front_face;
    }

    public ColorRGB returnColor(){
        if (t ==  Integer.MAX_VALUE){
            return Scene.getSkyBox().returnColorAt(point, normal);
        }
        return sceneObject.returnColor(this);
    }

    public CollisionInformation(Vector3 point, Vector3 normal) {
        this.point = point;
        this.normal = normal;
        t = Integer.MAX_VALUE;
    }
}
