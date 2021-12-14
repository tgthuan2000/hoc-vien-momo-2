/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.BUS;

import Client.KeyRSA_AES;
import Client.Status;
import Shares.Key;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author thanh
 */
public class RSA_AESBUS {

    public RSA_AESBUS() {
    }
    
    public int sendRequestGetPublicKey(){
        try {
            BUS.connect();
            BUS.writeLine(Key.REQUEST_GET_PUBLICKEY);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
    
    public int encryptAesKey() throws NoSuchAlgorithmException, Exception{
        try {
            BUS.connect();
            BUS.writeLine(Key.SEND_EN_SEC_KEY);
            KeyRSA_AES.keyRSA = castSkToString(generatorSK());
            System.out.println("Secret key be generater : " +KeyRSA_AES.keyRSA);
            String keyEncryt = encryptMessage(KeyRSA_AES.keyRSA, KeyRSA_AES.publicKey);
            System.out.println("Secret key encrypted : " +keyEncryt);
            BUS.writeLine(keyEncryt);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
    
    public SecretKey generatorSK() throws NoSuchAlgorithmException{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }
    
    public String castSkToString(SecretKey secretKey){
        byte[] publicKeyByte = secretKey.getEncoded();
        // Base64 encoded string

        Base64.Encoder encoder = Base64.getEncoder();
        String keyStr = encoder.encodeToString(secretKey.getEncoded());
        
        return keyStr;
    }
    
     private static String encryptMessage(String plainText,PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }
     
    public static String encrypt(String strToEncrypt, String myKey) {
      try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      } catch (Exception e) {
            System.out.println(e.toString());
      }
      return null;
    }
    
    public static String decrypt(String strToDecrypt, String myKey) {
      try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      } catch (Exception e) {
            System.out.println(e.toString());
      }
      return null;
}
}
