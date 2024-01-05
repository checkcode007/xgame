package com.xirui.jwt;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.apple.itunes.storekit.client.APIException;
import com.apple.itunes.storekit.client.AppStoreServerAPIClient;
import com.apple.itunes.storekit.model.*;
import com.apple.itunes.storekit.verification.SignedDataVerifier;
import com.apple.itunes.storekit.verification.VerificationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import java.util.HashMap;

public class APIExample {
    public static final String issuerId = "9f078401-fb14-4444-83ee-8acefaff4187";
    public static final  String keyId = "55W8B8F29J";
    public static final  String bundleId = "com.layla.chat";
    public static final  Path filePath = Path.of("/Users/mac/Desktop/1/SubscriptionKey_55W8B8F29J.p8");
    public static void main(String[] args) throws Exception {
        look();
//        String sno ="710001491280173";
//        transcationHistroy(sno);
//        transcation();


    }

    private static void transcationHistroy(String  transcationId) throws IOException {
        String encodedKey = Files.readString(filePath);
        Environment environment = Environment.PRODUCTION;
        AppStoreServerAPIClient client =
                new AppStoreServerAPIClient(encodedKey, keyId, issuerId, bundleId, environment);
        try {
            TransactionHistoryRequest req = new TransactionHistoryRequest();
            HistoryResponse response = client.getTransactionHistory(transcationId,null,req);
            System.out.println(response);
            for (String s : response.getSignedTransactions()) {
                String res_signedPayload = s;
                String signedPayload=new String(Base64.decodeBase64(res_signedPayload.split("\\.")[0]));
                JSONObject jsonObject=JSONObject.parseObject(signedPayload);
                JSONArray jsonArray = jsonObject.getJSONArray("x5c");
                Jws<Claims> result=JwtUtils.verifyJWT(jsonArray.get(0).toString(),res_signedPayload);
                String body = result.getBody().toString();
                System.err.println(body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void transcation() throws IOException {
        String encodedKey = Files.readString(filePath);
        Environment environment = Environment.PRODUCTION;
        AppStoreServerAPIClient client =
                new AppStoreServerAPIClient(encodedKey, keyId, issuerId, bundleId, environment);
        try {
//            TransactionInfoResponse response = client.getTransactionInfo("580001254980288");
            TransactionInfoResponse response = client.getTransactionInfo("580001254980288");

            System.out.println(response);
            String res_signedPayload = response.getSignedTransactionInfo();
            String signedPayload=new String(Base64.decodeBase64(res_signedPayload.split("\\.")[0]));
            JSONObject jsonObject=JSONObject.parseObject(signedPayload);
            JSONArray jsonArray = jsonObject.getJSONArray("x5c");
            Jws<Claims> result=JwtUtils.verifyJWT(jsonArray.get(0).toString(),res_signedPayload);
            String body = result.getBody().toString();
            System.err.println(body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void refund() throws IOException {
        String encodedKey = Files.readString(filePath);
        Environment environment = Environment.PRODUCTION;
        AppStoreServerAPIClient client =
                new AppStoreServerAPIClient(encodedKey, keyId, issuerId, bundleId, environment);
        try {
            RefundHistoryResponse response = client.getRefundHistory("450001627946929",null);
            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void look() throws IOException {
        String encodedKey = Files.readString(filePath);
        Environment environment = Environment.PRODUCTION;
        AppStoreServerAPIClient client =
                new AppStoreServerAPIClient(encodedKey, keyId, issuerId, bundleId, environment);
        try {
            OrderLookupResponse response = client.lookUpOrderId("MVL7SM3H7Y");
            System.out.println();
            System.out.println(response);

            for (String s : response.getSignedTransactions()) {
                String res_signedPayload = s;
                String signedPayload=new String(Base64.decodeBase64(res_signedPayload.split("\\.")[0]));
                JSONObject jsonObject=JSONObject.parseObject(signedPayload);
                JSONArray jsonArray = jsonObject.getJSONArray("x5c");
                Jws<Claims> result=JwtUtils.verifyJWT(jsonArray.get(0).toString(),res_signedPayload);
                String body = result.getBody().toString();
                System.err.println(body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
