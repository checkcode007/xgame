import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSATimeStampedEncryption {

    public static void main(String[] args) throws Exception {
        // 生成RSA密钥对
        KeyPair keyPair = generateKeyPair();

        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 原始数据
        String originalData = "Hello, RSA!";

        // 添加时间戳
        String dataWithTimestamp = addTimestamp(originalData);

        // 使用公钥加密数据
        byte[] encryptedData = encrypt(dataWithTimestamp, publicKey);
        System.out.println("Encrypted Data: " + Base64.getEncoder().encodeToString(encryptedData));

        // 使用私钥解密数据
        String decryptedData = decrypt(encryptedData, privateKey);

        // 验证时间戳
        if (isValidTimestamp(decryptedData)) {
            System.out.println("Decrypted Data: " + decryptedData);
        } else {
            System.out.println("Invalid timestamp. The data may be tampered.");
        }
    }

    // 生成RSA密钥对
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // 设置密钥长度
        return keyPairGenerator.generateKeyPair();
    }

    // 使用公钥加密数据
    public static byte[] encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    // 使用私钥解密数据
    public static String decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    // 添加时间戳
    public static String addTimestamp(String data) {
        long timestamp = System.currentTimeMillis();
        return data + "_" + timestamp;
    }

    // 验证时间戳
    public static boolean isValidTimestamp(String data) {
        String[] parts = data.split("_");
        if (parts.length == 2) {
            long timestamp = Long.parseLong(parts[1]);
            long currentTimestamp = System.currentTimeMillis();
            // 假设时间戳有效期为5分钟
            long validityPeriod = 5 * 60 * 1000; // 5 minutes in milliseconds
            return (currentTimestamp - timestamp) <= validityPeriod;
        }
        return false;
    }
}
