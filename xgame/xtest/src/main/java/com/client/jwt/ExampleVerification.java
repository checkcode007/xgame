package com.client.jwt;

import com.apple.itunes.storekit.model.Environment;
import com.apple.itunes.storekit.model.ResponseBodyV2DecodedPayload;
import com.apple.itunes.storekit.verification.SignedDataVerifier;
import com.apple.itunes.storekit.verification.VerificationException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

public class ExampleVerification {
    public static void main(String[] args) throws Exception {
        String issuerId = "9f078401-fb14-4444-83ee-8acefaff4187";
        String keyId = "55W8B8F29J";
        String bundleId = "com.layla.chat";
        Environment environment = Environment.SANDBOX;
        Set<InputStream> rootCAs = Set.of(
                new FileInputStream("/path/to/rootCA1"),
                new FileInputStream("/path/to/rootCA2")
        );

        SignedDataVerifier signedPayloadVerifier = new SignedDataVerifier(rootCAs, bundleId, null, environment, true);
        String notificationPayload = "ey...";
        try {
            ResponseBodyV2DecodedPayload payload = signedPayloadVerifier.verifyAndDecodeNotification(notificationPayload);
            System.out.println(payload);
        } catch (VerificationException e) {
            e.printStackTrace();
        }
    }
}
