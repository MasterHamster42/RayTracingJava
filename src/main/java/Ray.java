import java.util.List;

public class Ray {
    Vector3 origin;
    Vector3 newOrigin;
    Vector3 direction;
    int depth;

    public ColorRGB shootRayAt(Vector3 shootAt){
        direction = shootAt;
        newOrigin = origin;
        double colourMultiplierAccumulator = 1;

        for (int i = 0; i < depth; i++) {
            List<SceneObject> possibleCollisions = Scene.getPossibleCollisions(newOrigin, direction);
            CollisionInformation collisionInformation = new CollisionInformation(newOrigin, direction);

            for (SceneObject sceneObject : possibleCollisions) {
                sceneObject.detectHit(newOrigin, direction, collisionInformation);
            }
            if (collisionInformation.t == Integer.MAX_VALUE){
                return collisionInformation.returnColor().multiplyThis(colourMultiplierAccumulator);
            }else {
                colourMultiplierAccumulator *= 0.5;

                direction = collisionInformation.normal.add(Vector3.randomUnitVector());
                newOrigin = collisionInformation.point;
            }

        }
        return new ColorRGB(0,0,0);
    }

    /**
     * Calculates color of position impacted by ray, should be called only in shootRayAt
     * @param collisionInformation collision of ray, with SceneObject
     * @return color of SceneObject at point of collision
     */
    private ColorRGB calculateColor(CollisionInformation collisionInformation){
        //no hit registered
        if (collisionInformation.t == Integer.MAX_VALUE){
            // returning background color
            //TODO should be returning color from skybox
            double t = 0.5*(direction.normalizeThis().y + 1.0);
            return (new ColorRGB(1.0, 1.0, 1.0)).multiplyThis((1.0-t)).addThis((new ColorRGB(0.5, 0.7, 1.0)).multiplyThis(t));
        }
        //something was hit
        else {
            return collisionInformation.returnColor();
        }
    }

    // Constructors
    public Ray(Vector3 origin, int depth){
        this.origin = origin;
        this.depth = depth;
    }
}
