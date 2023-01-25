package raytacer;

public abstract class SceneObject {

    protected Material material;
    protected Texture texture;

    public abstract double detectHit(Ray ray);

    public abstract ColorRGB returnColor(CollisionInformation collisionPoint);

    public abstract CollisionInformation hitInformation(Ray ray, Vector3 hitPosition);

    public Material getMaterial(){
        return material;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

}
