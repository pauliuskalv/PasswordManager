package Encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESCipher {
    public static SecretKeySpec generateKey(String password)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] key = password.getBytes("UTF-8");
            key = digest.digest(key);
            key = Arrays.copyOf(key, 16);

            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] encrypt(SecretKeySpec secretKey, byte[] bytes)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher.doFinal(bytes);
            /**
             * TODO
             * Do something different with the catch blocks
             * */
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        } catch (BadPaddingException e) {
            return null;
        } catch (IllegalBlockSizeException e) {
            return null;
        }
    }

    public static byte[] decrypt(SecretKeySpec secretKey, byte[] bytes)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher.doFinal(bytes);
            /**
             * TODO
             * Do something different with the catch blocks
             * */
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        } catch (BadPaddingException e) {
            return null;
        } catch (IllegalBlockSizeException e) {
            return null;
        }
    }
}
