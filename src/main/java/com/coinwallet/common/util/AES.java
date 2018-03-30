package com.coinwallet.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class AES {

    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static final String DESKEY = "PIlpNkvKoNGyM4CCGBA2gQ==";

    /**
     *
     */
    public static String getKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     *
     */
    public static Key toKey(byte[] keyByte) {
        return new SecretKeySpec(keyByte, KEY_ALGORITHM);
    }

    /**
     *
     *
     * @param data
     * @param base64Key
     */
    public static String encrypt(String data, String base64Key) throws InvalidKeyException,
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException {
        Key key = toKey(Base64.decodeBase64(base64Key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodedByte = cipher.doFinal(data.getBytes(ENCODING));
        return Base64.encodeBase64String(encodedByte);
    }

    /**
     *
     * @param data
     * @param base64Key
     */
    public static String decrypt(String data, String base64Key)  {
        try{
            Key key = toKey(Base64.decodeBase64(base64Key));
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encodedByte = cipher.doFinal(Base64.decodeBase64(data));
            return new String(encodedByte);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  null;

    }
}
