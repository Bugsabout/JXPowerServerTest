package wuxl.study.wsdemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;

/**
 * @program: wsclient
 * @author: 吴小龙
 * @create: 2020-06-15 21:42
 * @description: Jwt是java web token的认证工具类。其中主要包含了加密的算法，传递的基础信息，token过期时间设置和token解析认证
 */

public class JwtUtil {


    /**
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRE_TIME = 15*60*1000;

    /**
     * token私钥，用于加密
     */
    private static final String TOKEN_SECRET = "1234567890";

    /**
     * 生成签名,15分钟后过期
     * @param username
     * @param userId
     * @return
     */
    public static String sign(String username,String userId){
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        return JWT.create().withHeader(header).withClaim("loginName",username)
                .withClaim("userId",userId).withExpiresAt(date).sign(algorithm);
    }


    public static boolean verity(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }

    }

}
