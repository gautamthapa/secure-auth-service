package com.authservice.infrastructure.security;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Getter
@Component
public class RsaKeyProvider {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;


    public RsaKeyProvider() {
        try {

            this.privateKey = loadPrivateKey();
            this.publicKey = loadPublicKey();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA keys", e);
        }
    }

    private PublicKey loadPublicKey() throws Exception {
        String key = Files.readString(new ClassPathResource("keys/auth-public.pem").getFile().toPath());

        key = key
                .replace(
                        "-----BEGIN PUBLIC KEY-----",
                        ""
                )
                .replace(
                        "-----END PUBLIC KEY-----",
                        ""
                )
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private PrivateKey loadPrivateKey() throws Exception {
        String key = Files.readString(new ClassPathResource("keys/auth-private.pem").getFile().toPath());

        key = key
                .replace(
                        "-----BEGIN PRIVATE KEY-----",
                        ""
                )
                .replace(
                        "-----END PRIVATE KEY-----",
                        ""
                )
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}
