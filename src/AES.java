import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

//This class holds the methods for encrypting and decrypting the images and notes uploaded by the patient.
public class AES {
    private static SecretKeySpec secret_Key;// Secret key used for encryption and decryption
    private static byte[] key_array;    // Key array used to generate the secret key

    // Method responsible for generating the secret key from a given key string
    public static void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            // Get the bytes of the key string
            key_array = myKey.getBytes("UTF-8");
            // Generate a SHA-1 hash of the key bytes
            sha = MessageDigest.getInstance("SHA-1");
            key_array = sha.digest(key_array);
            // Truncate the key to the first 16 bytes
            key_array = Arrays.copyOf(key_array, 16);
            // Generate the secret key from the truncated key bytes
            secret_Key = new SecretKeySpec(key_array, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Method responsible for encrypting a string value using the secret key
    public static String encryption(final String stringToEncrypt, final String secret) {
        try {
            // Generate the secret key from the given secret
            setKey(secret);
            // Initialize the Cipher with the ENCRYPT_MODE flag and the secret key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret_Key);
            // Encrypt the string and return the encrypted bytes as a base64 encoded string
            return Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            // Print any error message to the console
            System.out.println("Error while encrypting: " + e);
        }
        return null;
    }

// Method responsible for encrypting a byte array using the secret key (method overloading)
    public static byte[] encryption(final byte[] bytesToEncrypt, final String secret) {
        try {
            // Generate the secret key from the given secret
            setKey(secret);
            // Initialize the Cipher with the ENCRYPT_MODE flag and the secret key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret_Key);
            // Encrypt the byte array and return the encrypted bytes
            return cipher.doFinal(bytesToEncrypt);
        } catch (Exception e) {
            // Print any error message to the console
            System.out.println("Error while encrypting: " + e);
        }
        return null;
    }

    // Method responsible for decrypting a string value using the secret key
    public static String decryption(final String stringToDecrypt, final String secret) {
        try {
            // Generate the secret key from the given secret
            setKey(secret);
            // Initialize the Cipher with the DECRYPT_MODE flag and the secret key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secret_Key);
            // Decode the base64 encoded string and decrypt it
            return new String(cipher.doFinal(Base64.getDecoder().decode(stringToDecrypt)));
        } catch (Exception e) {
            //displays an error message if the password is wrong
            JOptionPane.showMessageDialog(null, "The password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

// Method responsible for decrypting a byte array using the secret key (method overloading)
    public static byte[] decryption(final byte[] bytesToDecrypt, final String secret) {
        try {
            // Generate the secret key from the given secret
            setKey(secret);
            // Initialize the Cipher with the DECRYPT_MODE flag and the secret key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secret_Key);
            // Decrypt the byte array and return the decrypted bytes
            return cipher.doFinal(bytesToDecrypt);
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
            //displays an error message if the password is wrong
           // JOptionPane.showMessageDialog(null, "The password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

}

