public abstract class SceneObject {

    protected static double Epsilon = Math.pow(10, -10);

    public abstract boolean detectHit(Vector3 origin, Vector3 direction, CollisionInformation collisionInformation);

    public abstract ColorRGB returnColor(CollisionInformation collisionPoint);

}
