package net.xiaodiclass.xdclass.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.xiaodiclass.xdclass.domain.User;

import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtils {
    public static final String SUBJECT = "xdclass";//主题
    public static final Long EXPIRE=1000*60*60*7L;//过期时间  一星期
    public static final String APPSECRET="xd666";

    /**
     * 生成jwt
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user){
        if(user==null||user.getId()==null||user.getHeadImg()==null||user.getName()==null){
            return null;
        }
        String token=Jwts.builder().setSubject(SUBJECT)//主题
                .claim("id",user.getId())//宣称
                .claim("name",user.getName())//宣称
                .claim("img",user.getHeadImg())//宣称
                .setIssuedAt(new Date())//发布时间
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))//过期时间
                .signWith(SignatureAlgorithm.HS256,APPSECRET)//签名，参数1：加密算法，参数2：盐
                .compact();//压缩
        return token;
    }

    public static Claims checkJWT(String token){
        try {
            final Claims claims=Jwts.parser()
                    .setSigningKey(APPSECRET)//盐
                    .parseClaimsJws(token).getBody();//解析宣称
            return claims;
        }catch (Exception e){}
        return null;
    }
}
