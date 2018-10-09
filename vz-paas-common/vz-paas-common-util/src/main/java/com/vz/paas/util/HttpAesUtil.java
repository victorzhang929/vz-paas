package com.vz.paas.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.vz.paas.util.exception.HttpAesException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Http Aes加密工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 11:31:23
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpAesUtil {

    private static final String CHAR_SET = "UTF-8";
    private static final String MD5 = "MD5";
    private static final String AES = "AES";

    /**
     * 加密
     * @param contentParam 需要加密的内容
     * @param keyParam 加密密码
     * @param md5Key 是否对key进行md5加密
     * @param ivParam 加密向量
     * @return 加密后字符串
     */
    public static String encrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
        try {
            byte[] content = contentParam.getBytes(CHAR_SET);
            byte[] key = keyParam.getBytes(CHAR_SET);
            byte[] iv = ivParam.getBytes(CHAR_SET);

            if (md5Key) {
                MessageDigest md5 = MessageDigest.getInstance(MD5);
                key = md5.digest(key);
            }

            SecretKeySpec sks = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, sks, ivps);

            byte[] bytes = cipher.doFinal(content);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception e) {
            log.error("加密密码失败", e);
            throw new HttpAesException("加密失败");
        }
    }

    /**
     * 解密
     * @param contentParam 需要解密的内容
     * @param keyParam 解密密码
     * @param md5Key 是否对key进行解密
     * @param ivParam 解密向量
     * @return 解密后字符串
     */
    public static String decrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
        try {
            if (StringUtils.isAnyBlank(contentParam, keyParam, ivParam)) {
                return "";
            }

            byte[] content = new BASE64Decoder().decodeBuffer(contentParam);
            byte[] key = keyParam.getBytes(CHAR_SET);
            byte[] iv = ivParam.getBytes(CHAR_SET);

            if (md5Key) {
                MessageDigest md5 = MessageDigest.getInstance(MD5);
                key = md5.digest(key);
            }

            SecretKeySpec sks = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, sks, ivps);

            byte[] bytes = cipher.doFinal(content);
            return new String(bytes, CHAR_SET);
        } catch (Exception e) {
            log.error("解密密码失败", e);
            throw new HttpAesException("解密失败");
        }
    }

}
