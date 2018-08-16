
package batplayer;

import java.io.File;
import java.net.URL;
import javafx.util.Duration;
import static java.util.Locale.filter;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import static sun.audio.AudioPlayer.player;

/**
 *
 * @author gurpreet9001
 */
public class FXMLDocumentController implements Initializable {
    
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
    private void handleButtonAction(ActionEvent event) {
       
       FileChooser fc=new FileChooser();
       FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("Select a File (*.mp4)","*.mp4");
       fc.getExtensionFilters().add(filter);
       File file=fc.showOpenDialog(null);
       filePath =file.toURI().toString();
       
       if(filePath !=null){
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
    private void exitVideo(ActionEvent event){
        System.exit(0);
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
        // TODO
    }    
    
}
