import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Scene {
    private static final List<SceneObject> sceneObjectsList = new ArrayList<>();
    private static SkyBox skyBox = new SkyBox();

    //TODO should be returning objects, queried from tree, based on received parameters of the ray(only possible collisions)
    public static List<SceneObject> getPossibleCollisions(Vector3 origin, Vector3 direction){
        return sceneObjectsList;
    }

    //TODO not finished, should be creating new tree with sceneObjectsList
    public static void loadScene(){
        //Will add objects to tree structure in future
    }

    public static void addObjects(Collection<SceneObject> sceneObjects){
        sceneObjectsList.addAll(sceneObjects);
    }

    public static void addObject(SceneObject sceneObject){
        sceneObjectsList.add(sceneObject);
    }

    //TODO not finished, should be adding sceneObjects to existing tree
    public static void loadObjects(Collection<SceneObject> sceneObjects){
        sceneObjectsList.addAll(sceneObjects);
    }
    //TODO not finished, should be adding sceneObject to existing tree
    public static void loadObject(SceneObject sceneObject){
        sceneObjectsList.add(sceneObject);
    }

    public static void addSkyBox(SkyBox newSkyBox){
        skyBox = newSkyBox;
    }

    public static SkyBox getSkyBox(){
        return skyBox;
    }


}
