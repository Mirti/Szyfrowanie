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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


/**
 * FXML Controller class
 *
 * @author M
 */
public class FenceSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int alphabetLength = alphabet.length;

    @FXML
    private TextField txtKey;
    @FXML
    private TextArea txtInput;
    @FXML
    private TextArea txtResult;
    @FXML
    private RadioButton rbEncode;
    @FXML
    private RadioButton rbDecode;

    /**
     * Przypisanie RadioButtonów do jednej grupy
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final ToggleGroup group = new ToggleGroup();
        rbEncode.setToggleGroup(group);
        rbEncode.setSelected(true);
        rbDecode.setToggleGroup(group);
    }

     /*
    Metoda wywołująca szyfrowanie lub deszyfrowanie w zależności od wyboru użytkownika
    */
    @FXML
    private void process(){
        if(rbEncode.isSelected()) encode();
        else decode();
    }
    
    //Metoda szyfrująca podanym kluczem
    @FXML
    private void encode() {
        int key = validateKey(txtKey.getText());
        String text = txtInput.getText();
        String result = "";
        if (validateInput(text) && key != -1) {
            /*
            pos - pozycja w stringu
            c - szerokość, ile zmieści się "w jednej" lini płotka liter. 
            test sprawdza czy klucz i słowo nie mają reszty po dzieleniu i +1 zeby nie ucinało i wypełniało braki X
            */
            int c=0;
            double test = text.length()/(double)key;
            if (test % 1 != 0){
                c = (int)Math.ceil(test)+1;
            }else{
                c = (text.length()/key);
            }
            char codedText[][] = new char[key][c];
            int pos = 0;
            //przepisanie tekstu do tablicy dwuwymiarowej, litera po literze.
            for(int i = 0; i < c; i++)
            {
                for(int j = 0; j < key; j++)
                {
                if(pos != text.length())
                    codedText[j][i] = text.charAt(pos++);
                else
                    codedText[j][i] = 'x';
                }
            }
            //tablica do string
            for(int i = 0; i < key; i++)
            {
                for(int j = 0; j < c; j++)
                {
                    //można usunąć if i będą 'x' w szfrogramie
                    if(codedText[i][j] == 'x'){
                        result = result;
                    } else{
                        result += codedText[i][j];
                    }
                }
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadzony tekst musi zawierać wyłącznie znaki od a do z"
                    + "\n Klucz musi być całkowitą liczbą dodatnią");
            alert.showAndWait();
        }
        txtResult.setText(result);
    }
    
    //Metoda deszyfrująca podanym kluczem
    @FXML
    private void decode() {
        int key = validateKey(txtKey.getText());
        String text = txtInput.getText();
        String result = "";
        if (validateInput(text) && key != -1) {
            /*
            pos - pozycja w stringu
            c - szerokość, ile zmieści się "w jednej" lini płotka liter. 
            test - sprawdza czy klucz i słowo nie mają reszty po dzieleniu i +1 zeby nie ucinało i wypełniało braki X
            */
            int c = (text.length()/key);
            char decodedText[][] = new char[key][c];
            int pos = 0;
            for(int i = 0; i < key; i++)
            {
                for(int j = 0; j < c; j++)
                {
                    decodedText[i][j] = text.charAt(pos++);
                }
            }
            for(int i = 0; i < c; i++)
            {
                for(int j = 0; j < key; j++)
                {
                    result += decodedText[j][i];
                }
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadzony tekst musi zawierać wyłącznie znaki od a do z"
                    + "\n Klucz musi być całkowitą liczbą dodatnią");
            alert.showAndWait();
        }
        txtResult.setText(result);
    }

    /*
    Metoda walidująca poprawność klucza.
     */
    private static int validateKey(String key) {
        if (key.equals("")) return 3;
        try {
            int convertedInput = Integer.parseInt(key);
            if (convertedInput <= 0) {
                return -1;
            }
            return convertedInput;
        } catch (Exception e) {
            return -1;
        }
    }
    
    

    /*
    Metoda sprawdzająca, czy tekst wejściowy jest poprawny
     */
    private static boolean validateInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!isInAlphabet(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /*
    Pomocnicza metoda sprawdzająca, czy dany znak znajduje się w alfabecie
     */
    private static boolean isInAlphabet(char c) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return true;
            }
        }
        return false;
    }

    /*
    Metoda zwracająca numer podanej litery w alfabecie
    */
    private static int numberInAlphabet(char c) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
