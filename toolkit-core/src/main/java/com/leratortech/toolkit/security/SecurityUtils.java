package com.leratortech.toolkit.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Security utilities for Java Web / E-commerce applications.
 * <p>
 * Supports password hashing, verification, random token generation,
 * Base64 encoding/decoding, AES encryption/decryption.
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    private static final SecureRandom secureRandom = new SecureRandom();

    // -------------------------------------------------------------------------
    // SHA-256 Hash
    // -------------------------------------------------------------------------

    /** Generates SHA-256 hash of input string */
    public static String sha256(String input) {
        if (input == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 hashing failed", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // Random tokens / keys
    // -------------------------------------------------------------------------

    /** Generates random alphanumeric token of given length */
    public static String randomToken(int length) {
        if (length <= 0) return null;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(secureRandom.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /** Generates secure random 256-bit AES key, returns Base64 */
    public static String generateAESKeyBase64() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, secureRandom);
            SecretKey key = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("AES key generation failed", e);
        }
    }

    // -------------------------------------------------------------------------
    // Base64
    // -------------------------------------------------------------------------

    public static String base64Encode(String plainText) {
        if (plainText == null) return null;
        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64Decode(String base64) {
        if (base64 == null) return null;
        return new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
    }

    // -------------------------------------------------------------------------
    // AES-256-GCM encryption/decryption
    // -------------------------------------------------------------------------

    /**
     * Encrypts plaintext with AES-256-GCM.
     * @param plainText plaintext
     * @param base64Key AES key in Base64
     * @return base64 encoded ciphertext
     */
    public static String aesEncrypt(String plainText, String base64Key) {
        if (plainText == null || base64Key == null) return null;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            byte[] iv = new byte[12];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    /**
     * Decrypts AES-256-GCM ciphertext.
     * @param base64CipherText base64 encoded ciphertext
     * @param base64Key AES key in Base64
     * @return decrypted plaintext
     */
    public static String aesDecrypt(String base64CipherText, String base64Key) {
        if (base64CipherText == null || base64Key == null) return null;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            byte[] combined = Base64.getDecoder().decode(base64CipherText);

            byte[] iv = new byte[12];
            byte[] cipherBytes = new byte[combined.length - iv.length];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, iv.length, cipherBytes, 0, cipherBytes.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);

            byte[] decrypted = cipher.doFinal(cipherBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
}
