package com.tianmen.ngz.engine.guard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class StringFogTest {

    @Test
    public void encryptDecryptRoundTrip() {
        String plain = "hello 南天门 secret";
        String key = "ngzz-key";
        String cipher = StringFog.encrypt(plain, key);
        String decrypted = StringFog.decrypt(cipher, key);
        assertEquals(plain, decrypted);
    }

    @Test
    public void cipherHidesPlain() {
        String cipher = StringFog.encrypt("hello secret", "ngzz-key");
        assertFalse(cipher.contains("hello"));
    }

    @Test
    public void differentKeysProduceDifferentCipher() {
        String cipherA = StringFog.encrypt("same plain", "key-a");
        String cipherB = StringFog.encrypt("same plain", "key-b");
        assertNotEquals(cipherA, cipherB);
    }
}
