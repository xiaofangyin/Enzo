package com.enzo.commonlib.utils.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author zhangkai-iri
 */
public class SecurityUtil {

    public static byte[] MD5(byte[] input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(input);
            return md.digest();
        }
        return null;
    }

    public static String getMD5(byte[] input) {
        return ByteConverter.bytesToHexString(MD5(input));
    }

    public static String getMD5(String input) {
        return getMD5(input.getBytes());
    }

    public static String getFileMD5(String filename) {
        byte[] digest = MD5(filename);
        if (digest == null) {
            return null;
        }
        return ByteConverter.bytesToHexString(digest);
    }

    public static String getFileMD5(File file) {
        byte[] digest = MD5(file);
        if (digest == null) {
            return null;
        }
        return ByteConverter.bytesToHexString(digest);
    }

    public static String getMD5(InputStream inputStream) {
        byte[] digest = (byte[]) null;
        BufferedInputStream in = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            in = new BufferedInputStream(inputStream);

            int theByte = 0;
            byte[] buffer = new byte[1024];
            while ((theByte = in.read(buffer)) != -1) {
                md.update(buffer, 0, theByte);
            }
            digest = md.digest();
        } catch (Exception localException) {
            if (in != null)
                try {
                    in.close();
                } catch (Exception localException1) {
                }
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception localException2) {
                }
        }
        if (digest == null) {
            return null;
        }
        return ByteConverter.bytesToHexString(digest);
    }

    public static byte[] MD5(String filename) {
        return MD5(new File(filename));
    }

    public static byte[] MD5(File file) {
        BufferedInputStream in = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            in = new BufferedInputStream(new FileInputStream(file));

            int theByte = 0;
            byte[] buffer = new byte[1024];
            while ((theByte = in.read(buffer)) != -1) {
                md.update(buffer, 0, theByte);
            }
            in.close();

            byte[] arrayOfByte1 = md.digest();
            return arrayOfByte1;
        } catch (Exception localException1) {
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception localException3) {
                }
        }
        return null;
    }

    public static String DES_encrypt(String plain, String key) {
        try {
            SecureRandom sr = new SecureRandom();

            DESKeySpec dks = new DESKeySpec(MD5(key.getBytes()));

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(1, secretKey, sr);

            byte[] encryptedData = cipher.doFinal(plain.getBytes());

            return ByteConverter.bytesToHexString(encryptedData);
        } catch (Exception localException) {
        }

        return "";
    }

    public static String DES_decrypt(String encrypted, String key) {
        try {
            SecureRandom sr = new SecureRandom();

            DESKeySpec dks = new DESKeySpec(MD5(key.getBytes()));

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(2, secretKey, sr);

            byte[] decryptedData = cipher.doFinal(ByteConverter.hexStringToBytes(encrypted));

            return new String(decryptedData);
        } catch (Exception localException) {
        }

        return "";
    }

    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
//
//    /**
//     * DES???????????????
//     *
//     * @param data
//     *            ??????????????????
//     * @param key
//     *            ????????????????????????????????????8???
//     * @return ???????????????????????????????????????Base64????????????
//     * @throws CryptException
//     *             ??????
//     */
//    public static String encodeWithBase64(String key, byte[] data){
//        String str = "";
//        try {
//            DESKeySpec dks = new DESKeySpec(key.getBytes());
//
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//            //key????????????????????????8?????????
//            Key secretKey = keyFactory.generateSecret(dks);
//            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
//            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
//            AlgorithmParameterSpec paramSpec = iv;
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
//
//            byte[] bytes = cipher.doFinal(data);
//            str = new String(Base64.encodeBase64(bytes));
//            //	      return byte2hex(bytes);
//        } catch (Exception e) {
//        }
//        return str;
//    }
//
//    /**
//     * DES???????????????
//     *
//     * @param data
//     *            ??????????????????
//     * @param key
//     *            ????????????????????????????????????8???
//     * @return ????????????????????????
//     * @throws Exception
//     *             ??????
//     */
//    public static byte[] decodeWithBase64(String key, byte[] data){
//        byte[] b = null;
//        try {
//            SecureRandom sr = new SecureRandom();
//            DESKeySpec dks = new DESKeySpec(key.getBytes());
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//            //key????????????????????????8?????????
//            Key secretKey = keyFactory.generateSecret(dks);
//            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
//            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
//            AlgorithmParameterSpec paramSpec = iv;
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
//            b = cipher.doFinal(data);
//        } catch (Exception e) {
//        }
//        return b;
//    }
//
//    /**
//     * DES???????????????
//     *
//     * @param data
//     *            ??????????????????
//     * @param key
//     *            ????????????????????????????????????8???
//     * @return ???????????????????????????????????????Base64????????????
//     * @throws CryptException
//     *             ??????
//     */
//    public static String encodeValue(String data, String key) {
//        return encodeWithBase64(key, data.getBytes());
//    }
//
//    /**
//     * ?????????????????????
//     *
//     * @param key
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static String decodeValue(String data, String key) {
//        byte[] datas;
//        String value = null;
//        try {
//
//            datas = decodeWithBase64(key, Base64.decodeBase64(data.getBytes()));
//
//            value = new String(datas);
//        } catch (Exception e) {
//            value = "";
//        }
//        return value;
//    }
}
