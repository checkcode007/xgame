import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;

import javax.crypto.Cipher;

public class RSADemo {

    public static void main(String[] args) throws Exception {
        // 生成RSA密钥对
        KeyPair keyPair = generateKeyPair();

        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 原始数据
        String originalData = "Hello, RSA!===>time="+System.currentTimeMillis();

        // 使用公钥加密数据
        byte[] encryptedData = encrypt(originalData, publicKey);
        System.out.println("Encrypted Data: " + new String(encryptedData));

        // 使用私钥解密数据
        String decryptedData = decrypt(encryptedData, privateKey);
        System.out.println("Decrypted Data: " + decryptedData);

        // 使用私钥对数据进行签名
        byte[] signature = sign(originalData, privateKey);
        System.out.println("Signature: " + new String(signature));

        // 使用公钥验证签名
        boolean isSignatureValid = verifySignature(originalData, signature, publicKey);
        System.out.println("Signature Verification: " + isSignatureValid);


        for (int i = 0; i < 3; i++) {
            // 使用私钥解密数据
            decryptedData = decrypt(encryptedData, privateKey);
            System.out.println("Decrypted Data: " + decryptedData);
            String[] ss = decryptedData.split("time=");
            Thread.sleep(100);
            if(System.currentTimeMillis()-Long.parseLong(ss[1])>100){
                System.out.println("------------->fail");
            }

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

    // 使用私钥对数据进行签名
    public static byte[] sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return signature.sign();
    }

    // 使用公钥验证签名
    public static boolean verifySignature(String data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data.getBytes());
        return sig.verify(signature);
    }
}
