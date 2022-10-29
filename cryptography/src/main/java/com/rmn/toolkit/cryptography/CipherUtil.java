package com.rmn.toolkit.cryptography;

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class CipherUtil {

    private static final int MAX_DECRYPT_BLOCK = 128;
    public static String cipherDecryptedUtil(Cipher cipher, byte[] data) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal));
        }
       return sb.toString();
    }

    public static byte [] cipherEncryption (byte [] data , Cipher cipher){
        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += 64) {
            byte[] doFinal = new byte[0];
            try {
                doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,i + 64));
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                throw new RuntimeException(e);
            }
            enBytes = ArrayUtils.addAll(enBytes, doFinal);
        }
        return enBytes;
    }
}
