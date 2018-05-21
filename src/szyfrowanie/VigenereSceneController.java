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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author M
 */
public class VigenereSceneController implements Initializable {

    private static final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int alphabetLength = alphabet.length;
    private static char[][] table;

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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table = createTable();
        final ToggleGroup group = new ToggleGroup();
        rbEncode.setToggleGroup(group);
        rbEncode.setSelected(true);
        rbDecode.setToggleGroup(group);
    }

    /*
    Uruchamia metodę szyfrującą lub deszyfrującą, zależnie od wyboru użytkownika
    */
    @FXML
    private void process() {
        if (rbEncode.isSelected()) {
            encode();
        } else {
            decode();
        }
    }

    /*
    Metoda szyfrująca zadany tekst
    */
    @FXML
    private void encode() {
        String result = "";
        String input = txtInput.getText();
        String key = adjustKey(txtKey.getText(), input.length());
        if (validate(input) && validate(key)) {
            for (int i = 0; i < input.length(); i++) {
                int positionX = numberInAlphabet(input.charAt(i));
                int positionY = numberInAlphabet(key.charAt(i));
                result = result + table[positionX][positionY];
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadzony tekst musi zawierać wyłącznie znaki od a do z"
                    + "\n Klucz musi zawirać wyłącznie znaki od a do z");
            alert.showAndWait();
        }
        txtResult.setText(result);
    }

    /*
    Metoda deszyfrująca zadany tekst
    */
    @FXML
    private void decode() {
        String result = "";
        String input = txtInput.getText();
        String key = adjustKey(txtKey.getText(), input.length());
        if (validate(input) && validate(key)) {
            for (int i = 0; i < input.length(); i++) {
                int positionX = numberInAlphabet(key.charAt(i));
                //Szukanie w kolumnie
                for (int j = 0; j < alphabet.length; j++) {
                    if (table[positionX][j] == input.charAt(i)) {
                        result = result + table[0][j];
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadzony tekst musi zawierać wyłącznie znaki od a do z"
                    + "\n Klucz musi zawirać wyłącznie znaki od a do z");
            alert.showAndWait();
        }
        txtResult.setText(result);
    }

    /*
    Metoda tworząca tabelę liter
    */
    private static char[][] createTable() {
        char[][] table = new char[alphabet.length][alphabet.length];
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                table[i][j] = alphabet[(i + j) % alphabet.length];
            }
        }
        return table;
    }

    /*
    Metoda sprawdzająca, czy tekst wejściowy oraz klucz są poprawne
     */
    private static boolean validate(String input) {
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

    /*
    Metoda dostosowująca klucz do długości słowa wejściowego
    */
    private static String adjustKey(String key, int length) {
        char[] resultArray = new char[length];
        for (int i = 0; i < length; i++) {
            resultArray[i] = key.charAt(i % key.length());
        }
        return String.valueOf(resultArray);
    }
}
