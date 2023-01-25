package raytacer;

public class CollisionInformation{
    Vector3 point;
    Vector3 normal;
    double t;   // -1 means miss, -2 means absorbed
    boolean front_face;
    SceneObject sceneObject;

    public CollisionInformation(Vector3 point, Vector3 normal, double t, boolean front_face) {
        this.point = point;
        this.normal = normal;
        this.front_face = front_face;
    }

    //temporary
    public CollisionInformation(Vector3 normal) {
        this.normal = normal;
        this.point = null;
    }

    public CollisionInformation(double t){
        this.t = t;
    }

    public CollisionInformation(){
        this.point = null;
    }

}
