package raytacer;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Scene {
    private List<SceneObject> sceneObjectsList = new ArrayList<>();
    private  SkyBox skyBox = new SkyBox();

    //TODO should be returning objects, queried from tree, based on received parameters of the ray(only possible collisions)
    public  List<SceneObject> getPossibleCollisions(Ray ray){
        return sceneObjectsList;
    }

    //TODO should be creating new tree with sceneObjectsList
    public  void loadScene(){
        //Will add objects to tree structure in future
    }


    public  void addObjects(Collection<SceneObject> sceneObjects){
        sceneObjectsList.addAll(sceneObjects);
    }

    public  void addObject(SceneObject sceneObject){
        sceneObjectsList.add(sceneObject);
    }

    //TODO should be adding sceneObjects to existing tree
    public  void loadObjects(Collection<SceneObject> sceneObjects){
        sceneObjectsList.addAll(sceneObjects);
    }
    //TODO should be adding sceneObject to existing tree
    public  void loadObject(SceneObject sceneObject){
        sceneObjectsList.add(sceneObject);
    }

    public  void addSkyBox(SkyBox newSkyBox){
        skyBox = newSkyBox;
    }

    public  SkyBox getSkyBox(){
        return skyBox;
    }

    public void loadConfig(String path) throws IOException, ParseException {
        path += ".json";
        // look for file
        if (!new File(path).exists() || new File(path).isDirectory()) throw new RuntimeException(String.format("config file: '%s' can not be found", path));
        // create JSONObject instance
        String content = new String(Files.readAllBytes(Paths.get(path)));

        // load scene
        JSONObject scene = new JSONObject(content);
        JSONArray Textures = scene.getJSONArray("Textures");
        ArrayList<Texture> textures = new ArrayList<>();
        for (int i = 0; i < Textures.length(); i++) {
            JSONObject texture = (JSONObject) Textures.get(i);
            if (texture.get("type").equals("SolidColorTexture")){
                JSONArray colorRGB = texture.getJSONArray("colorRGB");
                textures.add(new SolidColorTexture(new ColorRGB(colorRGB)));
            } else if (texture.get("type").equals("ImageTexture")) {
                ImageTexture imageTexture = new ImageTexture(texture.getString("path"));
                imageTexture.setScale(texture.getDouble("scale"));
                textures.add(imageTexture);
            }
        }

        JSONArray Materials = scene.getJSONArray("Materials");
        ArrayList<Material> materials = new ArrayList<>();
        for (int i = 0; i < Materials.length(); i++) {
            JSONObject material = Materials.getJSONObject(i);
            if (material.getString("type").equals("RoughMaterial")){
                materials.add(new RoughMaterial(material.getDouble("roughness"), new ColorRGB(material.getJSONArray("emissive"))));
            } else if (material.getString("type").equals("Dielectric")) {
                materials.add(new Dielectric(material.getDouble("refraction"),new ColorRGB(material.getJSONArray("emissive"))));
            }
        }

        JSONArray SceneObjects = scene.getJSONArray("SceneObjects");
        ArrayList<SceneObject> sceneObjects = new ArrayList<>();
        for (int i = 0; i < SceneObjects.length(); i++) {
            JSONObject sceneObject = SceneObjects.getJSONObject(i);
            if (sceneObject.getString("type").equals("Sphere")){
                sceneObjects.add(new Sphere(sceneObject.getDouble("radius"),
                        new Vector3(sceneObject.getJSONArray("center")),
                        materials.get(sceneObject.getInt("materialID")),
                        textures.get(sceneObject.getInt("textureID"))));
            } else if (sceneObject.getString("type").equals("Plane")) {
                sceneObjects.add(new Plane( new Vector3(sceneObject.getJSONArray("normal")),
                        new Vector3(sceneObject.getJSONArray("center")),
                        materials.get(sceneObject.getInt("materialID")),
                        textures.get(sceneObject.getInt("textureID"))));
            } else if (sceneObject.getString("type").equals("Triangle")) {
                sceneObjects.add(new Triangle( new Vector3(sceneObject.getJSONArray("vertex1")),
                        new Vector3(sceneObject.getJSONArray("vertex2")),
                        new Vector3(sceneObject.getJSONArray("vertex3")),
                        materials.get(sceneObject.getInt("materialID")),
                        textures.get(sceneObject.getInt("textureID"))));
            }
        }
    this.sceneObjectsList = sceneObjects;
    }


}
