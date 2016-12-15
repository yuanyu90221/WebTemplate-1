package com.exfantasy.template.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 * AES 加解密工具
 * 
 * 參考: 
 * <a href="http://www.cnblogs.com/arix04/archive/2009/06/26/1511839.html">http://www.cnblogs.com/arix04/archive/2009/06/26/1511839.html</a>
 * 參考: 
 * <a href="http://ijecorp.blogspot.tw/2016/05/java-jce-aes-encryption-decryption-2016.html">http://ijecorp.blogspot.tw/2016/05/java-jce-aes-encryption-decryption-2016.html</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public class AESEncryptDecryptUtil {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
  private static final String keyForGenerateKey = "eizD6MV1GA87mzVO";
  private static final String ENCODE = "UTF-8";

  /**
   * 根據系統時間及預設的 key, 產生一組 AES 加密字串當做 Key 給 client
   * 
   * @return key 給前端加密用
   * @throws Exception
   */
  public static String generateKey() throws Exception {
    String systemTime = String.valueOf(System.currentTimeMillis());
    return encrypt(systemTime, keyForGenerateKey);
  }

  /**
   * 根據前端傳過來的 key, 轉換成時間
   * 
   * @param key 前端傳過來的 key
   * @return 時間
   * @throws Exception
   */
  public static long getTimeOfKey(String key) throws Exception {
    String strSystemTime = decrypt(key, keyForGenerateKey);
    return Long.parseLong(strSystemTime);
  }

  /**
   * 加密
   * 
   * @param srcToEncrypt 欲加密字串
   * @param key 金鑰
   * @return 加密後字串
   * @throws Exception
   */
  public static String encrypt(String srcToEncrypt, String key) throws Exception {
    byte[] raw = key.getBytes(ENCODE);
    SecretKey secretKey = new SecretKeySpec(raw, ALGORITHM);

    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, getIV(raw));
    byte[] byteCipherText = cipher.doFinal(srcToEncrypt.getBytes(ENCODE));

    return Base64.getEncoder().encodeToString(byteCipherText);
  }

  /**
   * 解密
   * 
   * @param encryptedStr 欲解密字串
   * @param key 金鑰
   * @return 解密後字串
   * @throws Exception
   */
  public static String decrypt(String encryptedStr, String key) throws Exception {
    byte[] raw = key.getBytes(ENCODE);
    SecretKey secretKey = new SecretKeySpec(raw, ALGORITHM);

    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    // if throw InvalidKeyException, it's caused key must 16 bytes multiple
    cipher.init(Cipher.DECRYPT_MODE, secretKey, getIV(raw));
    byte[] cipherText = Base64.getDecoder().decode(encryptedStr);
    // if throw BadPaddingException, it's `encryptedStr` or `key` error.
    byte[] decryptedText = cipher.doFinal(cipherText);
    return new String(decryptedText, ENCODE);
  }

  private static IvParameterSpec getIV(byte[] raw) {
    byte[] iv = new byte[16];
    for (int i = 0; i < iv.length; i++) {
      iv[i] = raw[i];
    }
    return new IvParameterSpec(iv);
  }
}
