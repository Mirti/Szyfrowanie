/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szyfrowanie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author M
 */
public class MainSceneController implements Initializable {

    private Stage stage;
    @FXML
    private AnchorPane mainPane; 
    
    @FXML
    private void openCaesarScene() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/CaesarScene.fxml"));
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("Scenes/CaesarScene.fxml"));
        mainPane.getChildren().add(newLoadedPane);    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
