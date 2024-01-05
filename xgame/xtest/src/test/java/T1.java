import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
public class T1 {

    public static void main(String[] args) throws Exception {
        // 生成对称加密的密钥
        SecretKey secretKey = generateSecretKey();

        // 需要加密的明文
        String plaintext = "Hello, this is a secret message.";

        // 加密明文
        byte[] encryptedData = encrypt(plaintext, secretKey);

        // 解密密文
        String decryptedText = decrypt(encryptedData, secretKey);

        System.out.println("Original: " + plaintext);

        System.out.println("Decrypted: " + decryptedText);
        for (int i = 0; i <3 ; i++) {
            decryptedText = decrypt(encryptedData, secretKey);
            System.out.println("Decrypted: " + decryptedText);
        }

    }

    // 生成对称加密的密钥
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        return keyGenerator.generateKey();
    }

    // 使用对称密钥加密数据
    public static byte[] encrypt(String plaintext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plaintext.getBytes());
    }

    // 使用对称密钥解密数据
    public static String decrypt(byte[] ciphertext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }
}
