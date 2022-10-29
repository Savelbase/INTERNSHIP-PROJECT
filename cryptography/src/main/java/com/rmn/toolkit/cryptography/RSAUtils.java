package com.rmn.toolkit.cryptography;

import lombok.*;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RSAUtils {
    /**
     * RSA encryption algorithm
     */
    public static String KEY_ALGORITHM = "RSA";
    /**
     * Getting a public key
     */
    private String PUBLIC_KEY ;
    /**
     * Getting a private key
     */
    private String PRIVATE_KEY ;

    public String decryptedByPrivateKey(String encryptedMessage) throws Exception {
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(PRIVATE_KEY);
        PKCS8EncodedKeySpec pkcs8EncodeSpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key encodedPrivateKey = keyFactory.generatePrivate(pkcs8EncodeSpec);
        byte [] encryptedData = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, encodedPrivateKey);
        return  CipherUtil.cipherDecryptedUtil(cipher , encryptedData);
    }

    public String encryptedDataByPublicKey(String data) throws Exception {
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(PUBLIC_KEY);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Key publicKeyObj = keyFactory.generatePublic(x509);
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyObj);
        return encode(CipherUtil.cipherEncryption(data.getBytes() , cipher));
    }

    @SneakyThrows
    public String encode(byte [] data){
      return Base64.getEncoder().encodeToString(data);
    }

    @SneakyThrows
    public byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

}

