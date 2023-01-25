package raytacer;

public abstract class Material {
    public ColorRGB emmissive;

    public abstract Vector3 reflect(Vector3 direction, CollisionInformation collisionInformation);

}
