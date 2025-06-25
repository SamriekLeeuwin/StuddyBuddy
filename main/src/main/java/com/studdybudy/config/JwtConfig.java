package com.studdybudy.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@Slf4j
@Getter
public class JwtConfig {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    private static final String PRIVATE_KEY_PATH = "C:/Users/Megan/Downloads/app.key";
    private static final String PUBLIC_KEY_RESOURCE = "jwt/app.pub";

    @PostConstruct
    public void loadKeys() {
        try {
            //  Private key
            String privKeyPEM = new String(Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH)))
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] privDecoded = Base64.getDecoder().decode(privKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privDecoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

            //  Public key  classpath (resources)
            try (var pubStream = getClass().getClassLoader().getResourceAsStream(PUBLIC_KEY_RESOURCE)) {
                if (pubStream == null) {
                    throw new IllegalStateException("Public key (jwt/app.pub) not found in resources.");
                }

                String pubKeyPEM = new String(pubStream.readAllBytes())
                        .replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s", "");

                byte[] pubDecoded = Base64.getDecoder().decode(pubKeyPEM);
                X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubDecoded);
                this.publicKey = (RSAPublicKey) keyFactory.generatePublic(pubSpec);
            }

            //  Validate
            validateKeyPair();

            log.info(" JWT keys loaded successfully");
            log.info("Private key algorithm: {}", privateKey.getAlgorithm());
            log.info("Public key algorithm: {}", publicKey.getAlgorithm());
            log.info(" Key size: {} bits", publicKey.getModulus().bitLength());

        } catch (Exception e) {
            log.error(" Failed to load RSA keys for JWT", e);
            throw new RuntimeException("Failed to load RSA keys for JWT", e);
        }
    }

    private void validateKeyPair() {
        if (!publicKey.getModulus().equals(privateKey.getModulus())) {
            throw new RuntimeException("RSA key pair mismatch! Public and private keys are not from the same pair.");
        }
        log.info(" RSA key pair validation successful - keys match!");
    }
}
