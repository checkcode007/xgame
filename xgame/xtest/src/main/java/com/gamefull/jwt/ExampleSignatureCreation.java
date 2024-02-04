package com.gamefull.jwt;

import com.apple.itunes.storekit.offers.PromotionalOfferSignatureCreator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ExampleSignatureCreation {
    public static void main(String[] args) throws Exception {
        String keyId = "ABCDEFGHIJ";
        String bundleId = "com.example";
        Path filePath = Path.of("/Users/mac/Downloads/pocket-live-java_new/pocket-trade/src/main/resources/ioslayla/SubscriptionKey_55W8B8F29J.p8");;
        String encodedKey = Files.readString(filePath);

        PromotionalOfferSignatureCreator signatureCreator = new PromotionalOfferSignatureCreator(encodedKey, keyId, bundleId);

        String productId = "<product_id>";
        String subscriptionOfferId = "<subscription_offer_id>";
        String applicationUsername = "<application_username>";
        UUID nonce = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        String encodedSignature = signatureCreator.createSignature(productId, subscriptionOfferId, applicationUsername, nonce, timestamp);
        System.out.println(encodedSignature);
    }
}