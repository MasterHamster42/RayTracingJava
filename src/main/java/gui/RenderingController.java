package gui;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import raytacer.Engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.PrettyPrint.p;

public class RenderingController {
    public ImageView imageView;
    private int width;
    private int height;
    private Scene scene;
    private AnimationTimer renderLoop;
    private Engine engine;
    private boolean save = false;

    public void init(){
        System.out.println("innit?");
        App.getPrimaryStage().setHeight(Math.max(height, 400));
        App.getPrimaryStage().setWidth(Math.max(width, 400));
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();

                if (code.equals("ESCAPE")){
                    try {
                        returnToMenu();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (code.equals(("S"))){
                    save = true;
                }
            }
        });
        loop();
    }
//
    private void loop(){
        IntBuffer buffer = IntBuffer.allocate(width * height);
        int[] pixels = buffer.array();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(width, height, buffer, PixelFormat.getIntArgbPreInstance());
        WritableImage image = new WritableImage(pixelBuffer);
        imageView.setImage(image);
        imageView.fitHeightProperty();
        imageView.fitWidthProperty();
        final long[] lastTime = {0};
        int depth = Integer.parseInt(App.getAppController().Depth.getText());

//        Engine engine = new Engine(width, height, 10, depth, 90);

        renderLoop = new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
//                System.out.println(1_000_000_000/(currentNanoTime- lastTime[0]));
                lastTime[0] = currentNanoTime;
                buffer.clear();
                engine.returnFrame(pixels);
                // redraws the whole buffer
                pixelBuffer.updateBuffer(b -> null);
                if (save){
                    SaveFile(pixels);
                    save = false;
                }
            }
        };
        renderLoop.start();
    }

    private void SaveFile(int[] pixels){
        System.out.println("saving");
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int global_id = 0; global_id < pixels.length; global_id++) {
                    int i =height - global_id/(width) -1;
                    int j = global_id%(width);

                    bufferedImage.setRGB(j,height-i-1,pixels[global_id]);
            }

        try ( FileOutputStream fileOutputStream = new FileOutputStream("Renders/image"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) +".png")) {
            ImageIO.write(bufferedImage, "png", fileOutputStream);
        }
        catch (Exception e) {
            p("Error: %s", e.getMessage());
        }

    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public Scene getScene(){
        return scene;
    }

    public void returnToMenu() throws IOException {
        renderLoop.stop();
        Stage stage = App.getPrimaryStage();
        stage.setScene(App.getAppController().getScene());
        stage.show();
    }

}
