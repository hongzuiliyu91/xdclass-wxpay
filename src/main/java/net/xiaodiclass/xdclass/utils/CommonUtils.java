package net.xiaodiclass.xdclass.utils;

import javax.annotation.Generated;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CommonUtils {
    /**
     * 生成uuid，即用来标识一笔订单，也用作nonce_str
     * @return
     */
    public static String GeneratedUUID(){
        String uuid=UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
        return uuid;
    }

    public static String MD5(String data){
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            byte[] array=md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb=new StringBuilder();
            for(byte item:array){
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
