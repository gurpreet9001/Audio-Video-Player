
package batplayer;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;

/**
 *
 * @author gurpreet9001
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private HBox hbox;
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    private String filePath;
   
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekslider;
    
    @FXML
    private Label playTime;
    
    @FXML
    private Button playlistid;
    
     @FXML
    private StackPane sp;
    
    void playmyvideo(String filePath){
        
        
      Media media =new Media(filePath);
       mediaPlayer =new MediaPlayer(media);
       mediaView.setMediaPlayer(mediaPlayer);
       
       DoubleProperty width=mediaView.fitWidthProperty();
       DoubleProperty height=mediaView.fitHeightProperty();
       width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
       height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
       
       
       
       slider.setValue(mediaPlayer.getVolume()*100);
       
       slider.valueProperty().addListener(new InvalidationListener() {
           @Override
           public void invalidated(Observable observable) {
               mediaPlayer.setVolume(slider.getValue()/100);
           }
       });
       
       Stage stage = (Stage) mediaView.getScene().getWindow();
       mediaView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent doubleClicked) {
                 if(doubleClicked.getClickCount()==2 && stage.isFullScreen()==true){
                hbox.setVisible(true);
                seekslider.setVisible(true);
                 }
          
            else if(doubleClicked.getClickCount()==2 && stage.isFullScreen()==false){
                hbox.setVisible(false);
            seekslider.setVisible(false);
            
            
            }
            }
        });
       
      


       
      seekslider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(),
    mediaPlayer.totalDurationProperty()));
       
       
       seekslider.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
              mediaPlayer.seek(Duration.seconds(seekslider.getValue()));
           }
       });
       
       mediaPlayer.play();
       
   
       mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
           @Override
           public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if(seekslider.isPressed())
                    newValue=Duration.seconds(seekslider.getValue());
               seekslider.setValue(newValue.toSeconds());
              playTime.setText(formatTime(newValue, mediaPlayer.getMedia().getDuration()));
           }
       });
       
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
       FileChooser fc=new FileChooser();
       FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("Select a File (*.mp4)","*.mp4");
       fc.getExtensionFilters().add(filter);
       File file=fc.showOpenDialog(null);
       filePath =file.toURI().toString();
       
       if(filePath !=null){
         playmyvideo(filePath);
       }
    }
    
    @FXML
    private void keyPressed(KeyEvent event) {
            switch (event.getCode()) {
            case SPACE:
               mediaPlayer.pause();
                break;
            case ENTER:mediaPlayer.play();break;       
            default:
                break;
            }
            
        }
    
     @FXML
            public void dragcame(DragEvent event) {
                if (event.getDragboard().hasFiles()) {
                    /*
                     * allow for both copying and moving, whatever user chooses
                     */
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
                
            }
    
    @FXML
            public void dragdropped(DragEvent event) {
               Dragboard db = event.getDragboard();
                List<File> files = (ArrayList<File>) db.getContent(DataFormat.FILES);

                if (files != null) {
                    File file = files.get(0);
                     filePath =file.toURI().toString();
                
                    playmyvideo(filePath);
                }
                event.consume();
            }

    
    @FXML
    private void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }
    @FXML
    private void playVideo(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
    }
    @FXML
    private void fastVideo(ActionEvent event){
        mediaPlayer.setRate(1.5);
    }
    @FXML
    private void fasterVideo(ActionEvent event){
         mediaPlayer.setRate(2);
    }
    @FXML
    private void slowVideo(ActionEvent event){
        mediaPlayer.setRate(0.75);
    }
    @FXML
    private void slowerVideo(ActionEvent event){
        mediaPlayer.setRate(0.50);
    }
    @FXML
    private void screenshot(ActionEvent event){
  
        
         WritableImage writableImage = mediaView.snapshot(new SnapshotParameters(), null);
 
            File file = new File("capturedRoot.png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                System.out.println("Captured: " + file.getAbsolutePath());
            } catch (IOException ex) {
                
            }
    }
  
    @FXML
    private void makeplaylist(ActionEvent event) throws IOException{
         
         PlaylistFXMLController.arrlist.clear();
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlaylistFXML.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));  
        stage.show();

       // get a handle to the stage
       Stage stage1 = (Stage) playlistid.getScene().getWindow();
       // do what you have to do
       stage1.close();
       mediaPlayer.stop();
    }
    
    int i=0;
    
      @FXML
    private void next(ActionEvent event){
        mediaPlayer.stop();
        
        if(i<PlaylistFXMLController.arrlist.size()-1)
        i++;
        
        filePath =PlaylistFXMLController.arrlist.get(i);
                
      playmyvideo(filePath);
       
    }
    
    @FXML
    private void previous(ActionEvent event){
        mediaPlayer.stop();
        
        if(i>0)
        i--;
        
        filePath =PlaylistFXMLController.arrlist.get(i);
                
       playmyvideo(filePath);
       
    }
    
    @FXML
    private void startplaylist(ActionEvent event){
        
        filePath =PlaylistFXMLController.arrlist.get(i);
                
      playmyvideo(filePath);
       
    }
   
    
    private static String formatTime(Duration elapsed, Duration duration) {
   int intElapsed = (int)Math.floor(elapsed.toSeconds());
   int elapsedHours = intElapsed / (60 * 60);
   if (elapsedHours > 0) {
       intElapsed -= elapsedHours * 60 * 60;
   }
   int elapsedMinutes = intElapsed / 60;
   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
                           - elapsedMinutes * 60;
 
   if (duration.greaterThan(Duration.ZERO)) {
      int intDuration = (int)Math.floor(duration.toSeconds());
      int durationHours = intDuration / (60 * 60);
      if (durationHours > 0) {
         intDuration -= durationHours * 60 * 60;
      }
      int durationMinutes = intDuration / 60;
      int durationSeconds = intDuration - durationHours * 60 * 60 - 
          durationMinutes * 60;
      if (durationHours > 0) {
         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
            elapsedHours, elapsedMinutes, elapsedSeconds,
            durationHours, durationMinutes, durationSeconds);
      } else {
          return String.format("%02d:%02d/%02d:%02d",
            elapsedMinutes, elapsedSeconds,durationMinutes, 
                durationSeconds);
      }
      } else {
          if (elapsedHours > 0) {
             return String.format("%d:%02d:%02d", elapsedHours, 
                    elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes, 
                    elapsedSeconds);
            }
        }
    }
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                     
    }    
    
    
    
}
