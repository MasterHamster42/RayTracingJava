package raytacer;

import java.util.List;

public class RayGeneration {
    private final Scene scene;
    int depth;

    public ColorRGB shootRayAt(Ray ray){
        Vector3 reflectedRayDirection;
        ColorRGB DiffuseColors = new ColorRGB(1,1,1);
        ColorRGB emissiveColorAccumulation = new ColorRGB();

        for (int i = 0; i < depth; i++) {
            // detecting collision
            CollisionInformation collisionInformation = traceRay(ray);

            //calculating reflection vector
            reflectedRayDirection = newDirection(collisionInformation, ray);


            //there was no collision
            if (collisionInformation.t == -1){
                ColorRGB background = scene.getSkyBox().returnColorAt(ray.direction);
                DiffuseColors.multiplyThis(background);

                return emissiveColorAccumulation.addThis(DiffuseColors);
            }
            // ray was absorbed
            else if (collisionInformation.t == -2) {
                //absorption means no light reflected to us
                // return emissiveColorAccumulation + DiffuseColors * black, but black is (0,0,0) so it's equal to
                // return emissiveColorAccumulation + (0,0,0)
                return emissiveColorAccumulation;
            }
            // ray bounces
            else {
                ColorRGB emissiveColour = collisionInformation.sceneObject.material.emmissive;
                emissiveColorAccumulation.addThis(DiffuseColors.multiply(emissiveColour));

                // color of the object
                ColorRGB surfaceDiffuse = collisionInformation.sceneObject.returnColor(collisionInformation);
//                DiffuseColors.addThis(hitColor).multiplyThis(colourMultiplier);
                double dotProduct = reflectedRayDirection.dot(collisionInformation.normal);
                DiffuseColors.multiplyThis(surfaceDiffuse).multiplyThis(dotProduct*2);

                // new ray
                ray.direction = reflectedRayDirection;
                ray.origin = collisionInformation.point.addThis(ray.direction.multiply(0.00001));
            }
        }
        return DiffuseColors;
    }

    private CollisionInformation traceRay(Ray ray){
        List<SceneObject> possibleCollisions = scene.getPossibleCollisions(ray);
        double closestHitT = Double.MAX_VALUE;
        SceneObject closestObject = null;
        double currentDistance;

        // iteration over all possible objects
        for (SceneObject sceneObject : possibleCollisions) {

            //current hit distance
            currentDistance = sceneObject.detectHit(ray);

            //if material is not opaque, update the closest hit, if material is opaque call anyHit and based on this update closestHitT
            if (currentDistance != -1 && currentDistance < closestHitT){
                closestHitT = currentDistance;
                closestObject = sceneObject;
            }
        }
        //decide if we hit anything, then call closestHit, or if we missed, then call miss
        if (closestHitT == Double.MAX_VALUE){
            return miss(ray);
        }
        else {
            return closestHit(ray, closestObject, closestHitT);
        }
    }


    private CollisionInformation closestHit(Ray ray, SceneObject objectHit, double hitDistance){
        Vector3 hitPosition = ray.origin.add(ray.direction.multiply(hitDistance));
        return objectHit.hitInformation(ray, hitPosition);
    }

    private CollisionInformation miss(Ray ray){
        return new CollisionInformation(-1);
    }

    private Vector3 newDirection(CollisionInformation collisionInformation, Ray ray){
        if (collisionInformation.t == -1){
            return ray.direction.normalizeThis();
        }
        return collisionInformation.sceneObject.material.reflect(ray.direction, collisionInformation);
    }


    // Constructors
    public RayGeneration(int depth, Scene scene){
        this.scene = scene;
        this.depth = depth;
    }
}
