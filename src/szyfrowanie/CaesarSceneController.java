/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szyfrowanie;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author M
 */
public class CaesarSceneController implements Initializable {

    @FXML
    private TextField txtKey;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void encode(){
        System.out.print(validateKey(txtKey.getText()));
    }
    
    /*
    Metoda walidująca poprawność klucza.
    Liczby muszą być 
     */
    private static int validateKey(String input) {
        try {
            int convertedInput = Integer.parseInt(input);
            if (convertedInput <= 0) {
                return -1;
            }
            return convertedInput;
        } catch (Exception e) {
            return -1;
        }
    }
}

