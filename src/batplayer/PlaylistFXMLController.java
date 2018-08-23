/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batplayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author gurpreet9001
 */
public class PlaylistFXMLController implements Initializable {

    
    private String filePath;
    
     @FXML
    private Button playlistid;
    
    public static ArrayList<String> arrlist = new ArrayList<String>(30);
    int i=0;
    
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
         
            ObservableList<String> list = FXCollections.observableArrayList();
            
            @FXML
            ListView<String> myplaylist ;
            
            
         
            @FXML
            public void dragdropped(DragEvent event) {
               Dragboard db = event.getDragboard();
                List<File> files = (ArrayList<File>) db.getContent(DataFormat.FILES);

            
                if (files != null) {
                    File file = files.get(0);
                     filePath =file.toURI().toString();
                     //items.add(0, filePath);
                     list.removeAll(list);
                     list.add(filePath);
                     arrlist.add(filePath);
                     myplaylist.getItems().addAll(list);
                     
                }
   
                event.consume();
            }

             @FXML
    private void submitplaylist(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
Parent root1 = (Parent) fxmlLoader.load();
Stage stage = new Stage();
stage.setScene(new Scene(root1));  
stage.show();

// get a handle to the stage
 Stage stage1 = (Stage) playlistid.getScene().getWindow();
    // do what you have to do
    stage1.close();
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
