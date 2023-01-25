package raytacer;

public class RoughMaterial extends Material{

    public double roughness;

//    public Vector3 reflect(Vector3 direction, Vector3 normal){
//        Vector3 randomRay = normal.add(Vector3.randomUnitVector().multiplyThis(1)).normalizeThis();
//        Vector3 reflected = direction.subtract(randomRay.multiply(2*randomRay.dot(direction)));
//        // rey enters under the surface
//        if (normal.dot(reflected) < 0){
//            return reflected.multiplyThis(-1);
//        }
//        return reflected;
////        return direction.subtract(normal.multiply(2*normal.dot(direction)));
////        return normal.add(Vector3.randomUnitVector()).normalizeThis();
//    }

    public Vector3 reflect(Vector3 direction, CollisionInformation collisionInformation){
        Vector3 normal = collisionInformation.normal;
        Vector3 reflected = direction.subtract(normal.multiply(2*normal.dot(direction)));
        Vector3 randomOffset = Vector3.randomUnitVector().multiplyThis(roughness*1.99);
        randomOffset = normal.dot(randomOffset) > 0 ? randomOffset : randomOffset.multiplyThis(-1);
        return reflected.addThis(randomOffset).normalizeThis();
    }

    public RoughMaterial(double roughness, ColorRGB emissive){
        this.roughness = roughness;
        this.emmissive = emissive;
    }

    public RoughMaterial(){
        this(1, new ColorRGB());
    }
}
