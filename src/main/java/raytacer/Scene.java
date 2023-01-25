package raytacer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Scene {
    private  final List<SceneObject> sceneObjectsList = new ArrayList<>();
    private  final List<Material> sceneMaterialsList = new ArrayList<>();
    private  SkyBox skyBox = new SkyBox();

    //TODO should be returning objects, queried from tree, based on received parameters of the ray(only possible collisions)
    public  List<SceneObject> getPossibleCollisions(Ray ray){
        return sceneObjectsList;
    }

    //TODO not finished, should be creating new tree with sceneObjectsList
    public  void loadScene(){
        //Will add objects to tree structure in future
    }

    public  void addMaterial(Material material){
        sceneMaterialsList.add(material);
    }

    public  void addMaterials(Collection<Material> materials){
        sceneMaterialsList.addAll(materials);
    }

    public  Material getMaterial(int id){
        return sceneMaterialsList.get(id);
    }

    public  void addObjects(Collection<SceneObject> sceneObjects){
        sceneObjectsList.addAll(sceneObjects);
    }

    public  void addObject(SceneObject sceneObject){
        sceneObjectsList.add(sceneObject);
    }

    //TODO not finished, should be adding sceneObjects to existing tree
    public  void loadObjects(Collection<SceneObject> sceneObjects){
        sceneObjectsList.addAll(sceneObjects);
    }
    //TODO not finished, should be adding sceneObject to existing tree
    public  void loadObject(SceneObject sceneObject){
        sceneObjectsList.add(sceneObject);
    }

    public  void addSkyBox(SkyBox newSkyBox){
        skyBox = newSkyBox;
    }

    public  SkyBox getSkyBox(){
        return skyBox;
    }


}
