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
public class PlayfairSceneController implements Initializable {

    private static final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int alphabetLength = alphabet.length;
    private static char[][] table = new char[5][5];
    private static String[] pairs;

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
        final ToggleGroup group = new ToggleGroup();
        rbEncode.setToggleGroup(group);
        rbEncode.setSelected(true);
        rbDecode.setToggleGroup(group);
    }

    /*
    Metoda wybierająca na podstawie radiobuttona metodę szyfrującą lub deszyfrującą
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
    Metoda szyfrująca tekst
     */
    private void encode() {
        if (validate(txtKey.getText()) && validate(txtInput.getText())) {
            int[] position1, position2;
            String result = "";
            createTable(txtKey.getText());
            String value = txtInput.getText();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    System.out.print(table[i][j] + " ");
                }
                System.out.println();
            }
            pairs = makePairs(value);
            for (int i = 0; i < pairs.length; i++) {
                System.out.println(pairs[i]);
            }

            //główna pętla przechodząca po parach liter
            for (int i = 0; i < pairs.length; i++) {
                position1 = searchInTable(pairs[i].charAt(0));
                position2 = searchInTable(pairs[i].charAt(1));

                //ten sam wiersz
                if (position1[0] == position2[0]) {
                    result = result + table[position1[0]][(position1[1] + 1) % 5] + table[position2[0]][(position2[1] + 1) % 5];
                } else //ta sama kolumna
                if (position1[1] == position2[1]) {
                    result = result + table[(position1[0] + 1) % 5][position1[1]] + table[(position2[0] + 1) % 5][position2[1]];
                } else //inny wiersz i kolumna
                {
                    result = result + table[position1[0]][position2[1]] + table[position2[0]][position1[1]];
                }

            }
            txtResult.setText(result);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadzony tekst musi zawierać wyłącznie znaki od a do z, z wyłączeniem j"
                    + "\n Klucz musi zawirać wyłącznie znaki od a do z z wyłączeniem j");
            alert.showAndWait();
        }
    }

    /*
    Metoda deszyfrująca tekst
    */
    private void decode() {
        if (validate(txtKey.getText()) && validate(txtInput.getText())) {
            int[] position1, position2;
            String result = "";
            createTable(txtKey.getText());
            String value = txtInput.getText();
            pairs = makePairs(value);

            //główna pętla przechodząca po parach liter
            for (int i = 0; i < pairs.length; i++) {
                position1 = searchInTable(pairs[i].charAt(0));
                position2 = searchInTable(pairs[i].charAt(1));

                //ten sam wiersz
                if (position1[0] == position2[0]) {
                    result = result + table[position1[0]][(position1[1] - 1 + 5) % 5] + table[position2[0]][(position2[1] - 1 + 5) % 5];
                } else //ta sama kolumna
                if (position1[1] == position2[1]) {
                    result = result + table[(position1[0] - 1 + 5) % 5][position1[1]] + table[(position2[0] - 1 + 5) % 5][position2[1]];
                } else //inny wiersz i kolumna
                {
                    result = result + table[position1[0]][position2[1]] + table[position2[0]][position1[1]];
                }

            }
            txtResult.setText(result);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadzony tekst musi zawierać wyłącznie znaki od a do z z wyłączeniem j"
                    + "\n Klucz musi zawirać wyłącznie znaki od a do z z wyłączeniem j");
            alert.showAndWait();
        }
    }

    /*
    Metoda tworząca tabelę na z podmianą liter klucza
    */
    private static void createTable(String key) {
        String letters = key + String.copyValueOf(alphabet);
        letters = removeDuplicates(letters);
        int counter = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                table[i][j] = letters.charAt(counter);
                counter++;
            }
        }

    }

    //Szukanie w tablicy. Position[0] - pozycjaX, Position[1] - pozycja Y
    private static int[] searchInTable(char c) {
        int[] position = new int[2];
        position[0] = -1;
        position[1] = -1;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] == c) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    /*
    Metoda usuwająca powtarzające się litery z klucza
    */
    private static String removeDuplicates(String value) {
        String newValue = "";
        for (int i = 0; i < value.length(); i++) {
            if (!newValue.contains(String.valueOf(value.charAt(i)))) {
                newValue += value.charAt(i);
            }
        }
        return newValue;
    }

    /*
    Metoda zwracająca tablicę par kolejnych liter
    */
    private static String[] makePairs(String value) {
        if (value.length() % 2 != 0) {
            value = value + "x";
        }
        String[] pairs = new String[value.length() / 2];
        for (int i = 0; i < value.length(); i = i + 2) {
            pairs[i / 2] = new StringBuilder().append(value.charAt(i)).append(value.charAt(i + 1)).toString();
        }
        return pairs;
    }

    /*
    Metoda walidująca tekst do szyfrowania i klucz
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
}
