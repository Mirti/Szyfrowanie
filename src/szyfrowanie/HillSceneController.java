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
import org.ejml.*;
import org.ejml.data.DMatrixRMaj;
import org.ejml.simple.SimpleMatrix;

/**
 * FXML Controller class
 *
 * @author M
 */
public class HillSceneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int alphabetLength = alphabet.length;
    private SimpleMatrix keyMatrix;

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
    @FXML
    private RadioButton rb2, rb3;
    @FXML
    private TextField txt11, txt12, txt13, txt21, txt22, txt23, txt31, txt32, txt33;

    /**
     * Przypisanie RadioButtonów do jednej grupy
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final ToggleGroup group = new ToggleGroup();
        rbEncode.setToggleGroup(group);
        rbEncode.setSelected(true);
        rbDecode.setToggleGroup(group);

        final ToggleGroup group2 = new ToggleGroup();
        rb2.setToggleGroup(group2);
        rb2.setSelected(true);
        rb3.setToggleGroup(group2);
        hideMatrix();

    }

    @FXML
    private void showMatrix() {
        txt13.setVisible(true);
        txt23.setVisible(true);
        txt31.setVisible(true);
        txt32.setVisible(true);
        txt33.setVisible(true);
    }

    @FXML
    private void hideMatrix() {
        txt13.setVisible(false);
        txt23.setVisible(false);
        txt31.setVisible(false);
        txt32.setVisible(false);
        txt33.setVisible(false);
    }

    /*
    Metoda wywołująca szyfrowanie lub deszyfrowanie w zależności od wyboru użytkownika
     */
    @FXML
    private void process() {
        if (rbEncode.isSelected()) {
            encode();
        } else {
            decode();
        }
    }

    //Metoda szyfrująca podanym kluczem
    @FXML
    private void encode() {
        String result = "";
        createKeyMatrix(); //Tworzenie macierzy z klucza
        SimpleMatrix input, tempResult; //Macierz liczb wyrazu wejściowego

        double[] numbers = wordToNumbers(txtInput.getText());

        if (rb2.isSelected()) { //Dla macierzy 2x2
            for (int i = 0; i < numbers.length; i = i + 2) {
                input = new SimpleMatrix(2, 1, true, new double[]{numbers[i], numbers[i + 1]});
                tempResult = keyMatrix.mult(input); //mnożenie macierzy
                for (int j = 0; j < tempResult.getNumElements(); j++) {
                result = result + (alphabet[(int) tempResult.get(j) % alphabet.length]);
                }
            }
            txtResult.setText(result);
        }

        if (rb3.isSelected()) { //Dla macierzy 3x3

        }
    }

    //Metoda deszyfrująca podanym kluczem
    @FXML
    private void decode() {
        String result = "";
        createKeyMatrix(); //Tworzenie macierzy z klucza
        SimpleMatrix input, tempResult; //Macierz liczb wyrazu wejściowego

        double[] numbers = wordToNumbers(txtInput.getText());

        if (rb2.isSelected()) { //Dla macierzy 2x2
            for (int i = 0; i < numbers.length; i = i + 2) {
                input = new SimpleMatrix(2, 1, true, new double[]{numbers[i], numbers[i + 1]});
                tempResult = keyMatrix.invert().mult(input); //mnożenie macierzy
                //Usuwanie minusa z liczb
                tempResult.print();
                for (int j = 0; j < tempResult.getNumElements(); j++) {
                    if (tempResult.get(j) < 0) { //jeżeli liczba w macierzy mniejsza od 0
                        result = result +alphabet[modulo((int)tempResult.get(j), alphabet.length)];
                    }else{ //jeżeli większa
                        result = result + (alphabet[(int) tempResult.get(j) % alphabet.length]);
                    }
                }
            }
            txtResult.setText(result);
        }
    }

    /*
    Metoda walidująca poprawność klucza.
     */
    private static int validateKey(String key) {
        if (key.equals("")) {
            return 3;
        }
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

    //Metoda przekształcająca słowo na tablicę liczb
    private static double[] wordToNumbers(String word) {
        double[] numbers = new double[word.length()];
        for (int i = 0; i < word.length(); i++) {
            numbers[i] = numberInAlphabet(word.charAt(i));
        }
        return numbers;
    }

    //Metoda służąca do tworzenia macierzy z klucza podanego przez użytkownika
    private void createKeyMatrix() {
        double[] key = null;
        if (rb2.isSelected()) {
            key = new double[]{Double.valueOf(txt11.getText()), Double.valueOf(txt12.getText()), Double.valueOf(txt21.getText()), Double.valueOf(txt22.getText())};
            keyMatrix = new SimpleMatrix(2, 2, true, key);
        }
        if (rb3.isSelected()) {
            key = new double[]{Double.valueOf(txt11.getText()), Double.valueOf(txt12.getText()), Double.valueOf(txt13.getText()),
                Double.valueOf(txt21.getText()), Double.valueOf(txt22.getText()), Double.valueOf(txt23.getText()),
                Double.valueOf(txt31.getText()), Double.valueOf(txt32.getText()), Double.valueOf(txt33.getText())};
            keyMatrix = new SimpleMatrix(3, 3, true, key);
        }
    }
    
    private int modulo(int a, int b){
        int modulo= (((a % b) + b) % b);
        return modulo;
    }
}
