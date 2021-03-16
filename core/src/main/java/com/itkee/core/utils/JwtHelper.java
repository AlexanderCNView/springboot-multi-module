package com.itkee.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.itkee.core.annotation.User;
import com.itkee.core.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author rabbit
 */
public class JwtHelper {
    private Long EXPIRATION_TIME;
    private String SECRET;
    private final String TOKEN_PREFIX = "Bearer";

    public JwtHelper(String secret, long expire) {
        this.EXPIRATION_TIME = expire;
        this.SECRET = secret;
        System.out.println("正在初始化Jwthelper，expire="+expire);
    }

    public String generateToken(String... claims) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.SECOND, EXPIRATION_TIME.intValue());
        Date d = c.getTime();
        return JWT.create()
                .withAudience(claims)
                .withExpiresAt(d)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public User validateTokenAndGetClaims(HttpServletRequest request) throws AuthorizationException {
        String HEADERSTRING = "Authorization";
        Optional<String> token = Optional.ofNullable(request.getHeader(HEADERSTRING));
        if (!token.isPresent()) {
            throw new AuthorizationException("没有token数据");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            jwtVerifier.verify(token.get());
        } catch (JWTVerificationException e) {
            throw new AuthorizationException("token验证失败");
        }
        List<String> tokenList;
        try {
            tokenList = JWT.decode(token.get()).getAudience();
        } catch (JWTDecodeException jwtDecodeException){
            throw new RuntimeException("token数据异常");
        }

        return User.builder().userId(Integer.valueOf(tokenList.get(0))).roleId(Integer.valueOf(tokenList.get(1))).build();
    }
}