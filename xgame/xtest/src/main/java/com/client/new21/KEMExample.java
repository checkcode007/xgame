package com.client.new21;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
//import java.security.KeyAgreement;
import javax.crypto.KeyAgreement;

public class KEMExample {
    public static void main(String[] args) throws Exception {
        // 生成公私钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 创建 KeyAgreement 对象
        KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
        keyAgreement.init(keyPair.getPrivate());

        // 执行密钥协商过程
        keyAgreement.doPhase(keyPair.getPublic(), true);

        // 生成会话密钥
        byte[] sharedSecret = keyAgreement.generateSecret();

        // 封装会话密钥
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.WRAP_MODE, keyPair.getPublic());
//        byte[] wrappedKey = cipher.wrap(sharedSecret);
//
//        // 解封装会话密钥
//        cipher.init(Cipher.UNWRAP_MODE, keyPair.getPrivate());
//        byte[] unwrappedKey = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

        // 输出结果
//        System.out.println("Shared Secret: " + new String(sharedSecret));
//        System.out.println("Unwrapped Key: " + new String(unwrappedKey));
    }
}
