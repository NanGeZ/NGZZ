package com.tianmen.ngz.engine.guard;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class StringFog {

    private StringFog() {
    }

    public static String encrypt(String plain, String key) {
        byte[] keyBytes = keyBytes(key);
        byte[] plainBytes = plain.getBytes(StandardCharsets.UTF_8);
        byte[] xored = xor(plainBytes, keyBytes);
        return Base64.getEncoder().encodeToString(xored);
    }

    public static String decrypt(String cipher, String key) {
        byte[] keyBytes = keyBytes(key);
        byte[] cipherBytes = Base64.getDecoder().decode(cipher);
        byte[] plainBytes = xor(cipherBytes, keyBytes);
        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    private static byte[] keyBytes(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty");
        }
        return key.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] xor(byte[] input, byte[] key) {
        byte[] output = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = (byte) (input[i] ^ key[i % key.length]);
        }
        return output;
    }
}
