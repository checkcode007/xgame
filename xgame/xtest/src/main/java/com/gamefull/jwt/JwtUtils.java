package com.gamefull.jwt;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JwtUtils {
    private static final Logger logger = Logger.getLogger("JwtUtils");

    //解密回调的信息
    public static String verify(String jwt) {
        try {
            // 拿到 header 中 x5c 数组中第一个
            DecodedJWT decodedJWT = JWT.decode(jwt);
            String header = new String(Base64.getDecoder().decode(decodedJWT.getHeader()));
            String x5c0 = JSONObject.parseObject(JSONObject.parseObject(header).get("x5c").toString()).get(0).toString();
            // 获取公钥
            PublicKey publicKey = getPublicKeyByX5c(x5c0);
            // 验证 token
            Algorithm algorithm = Algorithm.ECDSA256((ECPublicKey) publicKey, null);
            algorithm.verify(decodedJWT);
            String payload = decodedJWT.getPayload();

            return payload;
        } catch (Exception e) {
        }
        return null;
    }

    public static PublicKey getPublicKeyByX5c(String x5c) throws CertificateException {
        byte[] x5c0Bytes = Base64.getDecoder().decode(x5c);
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(x5c0Bytes));
        return cer.getPublicKey();
    }

    public static Jws<Claims> verifyJWT(String x5c, String jws){
        try {
            X509Certificate cert = getCert(x5c);
            if (!cert.getSubjectDN().getName().contains("Apple Inc")){
                logger.log(Level.INFO,"not apple cert . name = {}", cert.getIssuerX500Principal().getName());
                return null;
            }

            return Jwts.parser().setSigningKey(cert.getPublicKey()).parseClaimsJws(jws);
        }catch (JwtException exc){
            logger.log(Level.INFO,"jws verify failure.");
            exc.printStackTrace();
            return null;
        } catch (Exception exc){
            logger.log(Level.INFO,"jws verify error.", exc);
            return null;
        }
    }
//    public static Jws<Claims> verifyJWT(String jws){
//        try {
//            X509Certificate cert = getCert(x5c);
//            if (!cert.getSubjectDN().getName().contains("Apple Inc")){
//                logger.log(Level.INFO,"not apple cert . name = {}", cert.getIssuerX500Principal().getName());
//                return null;
//            }
//            return Jwts.parser().setSigningKey(cert.getPublicKey()).parseClaimsJws(jws);
//        }catch (JwtException exc){
//            logger.log(Level.INFO,"jws verify failure.");
//            exc.printStackTrace();
//            return null;
//        } catch (Exception exc){
//            logger.log(Level.INFO,"jws verify error.", exc);
//            return null;
//        }
//    }
    public static X509Certificate getCert(String x5c) throws CertificateException {
        String stripped = x5c.replaceAll("-----BEGIN (.*)-----", "");
        stripped = stripped.replaceAll("-----END (.*)----", "");
        stripped = stripped.replaceAll("\r\n", "");
        stripped = stripped.replaceAll("\n", "");
        stripped.trim();
        byte[] keyBytes = Base64.getDecoder().decode(stripped);
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        return (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(keyBytes));
    }

    //加密算法


}
