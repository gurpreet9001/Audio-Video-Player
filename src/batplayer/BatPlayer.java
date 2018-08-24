/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batplayer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author gurpreet9001
 */
public class BatPlayer extends Application {

    
    
    static boolean isSplashLoaded=false;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent pane = FXMLLoader.load(getClass().getResource(("SplashFXML.fxml")));

        Scene scene = new Scene(pane);
        stage.setScene(scene);
      stage.show();
      
      
        
        stage.setTitle("BatPlayer");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("batman.png")));
        
        if (!BatPlayer.isSplashLoaded) {
               BatPlayer.isSplashLoaded = true;
            

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
               
                 try {
                    Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene1 = new Scene(root);
                 
        scene1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent doubleClicked) {
                 if(doubleClicked.getClickCount()==2 && stage.isFullScreen()==true){
                stage.setFullScreen(false);}
          
            else if(doubleClicked.getClickCount()==2){
                stage.setFullScreen(true);}
            }
        });
                stage.setScene(scene1);
      stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
       
        
    }
   
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
