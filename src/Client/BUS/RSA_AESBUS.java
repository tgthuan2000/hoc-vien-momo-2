package Client.BUS;

import Client.KeyRSA_AES;
import Client.Status;
import Client.WorkerClient;
import Shares.Key;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.Executors;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RSA_AESBUS {

    public RSA_AESBUS() {
    }

    public int sendRequestGetPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        try {
            //Receive public key RSA
            if (BUS.connect()) {
                KeyRSA_AES.publicKey = castStringToPublickey(BUS.in.readLine()); // read public key RSA
                KeyRSA_AES.keyAES = castSkToString(generatorSK());    // generate AES
                String encode = encryptMessage(KeyRSA_AES.keyAES, KeyRSA_AES.publicKey);
                //System.out.println("pulic key: " + KeyRSA_AES.publicKey);
                System.out.println("AES key: " + KeyRSA_AES.keyAES);
                System.out.println("Encode RSA_AES: " + encode);
                BUS.out.write(encode + "\n");
                BUS.flush();
                Executors.newFixedThreadPool(1).execute(new WorkerClient((BUS.socket)));
            }
            return Status.OK;

        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    private PublicKey castStringToPublickey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] publicKeyByteServer = decoder.decode(publicKeyStr.getBytes());
        // generate the publicKey
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKeyServer = (PublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByteServer));
        return publicKeyServer;
    }

    public int encryptAesKey() throws NoSuchAlgorithmException, Exception {
        try {
            BUS.connect();
            BUS.writeLine(Key.SEND_EN_SEC_KEY);
            KeyRSA_AES.keyAES = castSkToString(generatorSK());
            System.out.println("Secret key be generater : " + KeyRSA_AES.keyAES);
            String keyEncryt = encryptMessage(KeyRSA_AES.keyAES, KeyRSA_AES.publicKey);
            System.out.println("Secret key encrypted : " + keyEncryt);
            BUS.writeLine(keyEncryt);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    public SecretKey generatorSK() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    public String castSkToString(SecretKey secretKey) {
        // Base64 encoded string
        Base64.Encoder encoder = Base64.getEncoder();
        String keyStr = encoder.encodeToString(secretKey.getEncoded());

        return keyStr;
    }

    private static String encryptMessage(String plainText, PublicKey publicKey) throws Exception {
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
