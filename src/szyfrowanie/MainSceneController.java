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
import javafx.scene.control.Tab;
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
    private Tab caesarTab;
    @FXML
    private Tab fenceTab;
    @FXML
    private Tab playfairTab;
    @FXML
    private Tab vigenereTab;
    @FXML
    private Tab hillTab;
    @FXML
    private Tab beaufortTab;
    @FXML
    private Tab md5Tab;

    @FXML
    private void openCaesarScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/CaesarScene.fxml"));
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scenes/CaesarScene.fxml"));
        caesarTab.setContent(newLoadedPane);
    }

    @FXML
    private void openFenceScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/FenceScene.fxml"));
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scenes/FenceScene.fxml"));
        fenceTab.setContent(newLoadedPane);
    }

    @FXML
    private void openPlayfairScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/PlayfairScene.fxml"));
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scenes/PlayfairScene.fxml"));
        playfairTab.setContent(newLoadedPane);
    }

    @FXML
    private void openVigenereScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/VigenereScene.fxml"));
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scenes/VigenereScene.fxml"));
        vigenereTab.setContent(newLoadedPane);
    }

    @FXML
    private void openHillScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/HillScene.fxml"));
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scenes/HillScene.fxml"));
        hillTab.setContent(newLoadedPane);
    }

    @FXML
    private void openBeaufortScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/BeaufortScene.fxml"));
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scenes/BeaufortScene.fxml"));
        beaufortTab.setContent(newLoadedPane);
    }

    @FXML
    private void openMD5Scene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/MD5Scene.fxml"));
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scenes/MD5Scene.fxml"));
        md5Tab.setContent(newLoadedPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
