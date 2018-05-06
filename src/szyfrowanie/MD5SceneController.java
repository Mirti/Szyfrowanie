/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szyfrowanie;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.omg.IOP.Encoding;

/**
 * FXML Controller class
 *
 * @author M
 */
public class MD5SceneController implements Initializable {

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
    private byte[] arr;
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
    private void process() throws Exception{
        if(rbEncode.isSelected()) {
            encode();
        }
        else {
            decode();
        }
    }
    
    //Metoda szyfrująca podanym kluczem
    @FXML
     public void encode() throws Exception
    {
        String message = txtInput.getText();
        final MessageDigest digmsg = MessageDigest.getInstance("md5");
        final byte[] passDig = digmsg.digest("HG58YZ3CR9".getBytes("utf-8"));
        final byte[] byteArr = Arrays.copyOf(passDig, 24);
        for (int j = 0, k = 16; j < 8;) {
                byteArr[k++] = byteArr[j++];
        }
        
        final SecretKey mdkey = new SecretKeySpec(byteArr, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher szyfr = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        szyfr.init(Cipher.ENCRYPT_MODE, mdkey, iv);

        final byte[] arrNiekod = message.getBytes("utf-8");
        final byte[] arrKod = szyfr.doFinal(arrNiekod);
        arr = arrKod;
        txtResult.setText(arrKod.toString());
    } 

    //Metoda deszyfrująca podanym kluczem
    @FXML
    public void decode() throws Exception {
        String message = txtInput.getText();
        final MessageDigest digmsg = MessageDigest.getInstance("md5");
        final byte[] passDig = digmsg.digest("HG58YZ3CR9".getBytes("utf-8"));
        final byte[] byteArr = Arrays.copyOf(passDig, 24);
        for (int j = 0, k = 16; j < 8;) {
                byteArr[k++] = byteArr[j++];
        }

        final SecretKey mdkey = new SecretKeySpec(byteArr, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher szyfr = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        szyfr.init(Cipher.DECRYPT_MODE, mdkey, iv);

        final byte[] decodedArr = szyfr.doFinal(arr);

        String output =  new String(decodedArr, "utf-8"); 
        txtResult.setText(output);
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
