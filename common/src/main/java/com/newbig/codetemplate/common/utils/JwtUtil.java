package com.newbig.codetemplate.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.newbig.codetemplate.common.constant.AppConstant;
import com.newbig.codetemplate.common.exception.UserRemindException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.newbig.codetemplate.common.constant.AppConstant.TOKEN_HEADER;


@Slf4j
public class JwtUtil {

    static JWTVerifier verifier;
    static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256(AppConstant.SECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        verifier = JWT.require(algorithm)
                .withIssuer(AppConstant.ISSUER)
                .build(); //Reusable verifier instance
    }

    /**
     * 生成jwt token
     *
     * @param userUuid
     * @return
     * @throws IOException
     */
    public static String genToken(String mobile, String userUuid) {
        String token = JWT.create()
                .withIssuer(AppConstant.ISSUER)
                //凌晨两点过期
                .withNotBefore(DateTimeUtils.getDateToMidNight())
                .withClaim(AppConstant.USER_UUID, userUuid)
                .withClaim(AppConstant.MOBILE, mobile)
                .sign(algorithm);
        return token;
    }

    /**
     * 验证jwt token
     *
     * @param request
     * @throws IOException
     * @throws ServletException
     */
    public static DecodedJWT verifyToken(final HttpServletRequest request) {
        final String authHeader = request.getHeader(TOKEN_HEADER);
        if (StringUtil.isEmpty(authHeader)) {
            throw new UserRemindException("Token 不存在");
        }
        try {
            DecodedJWT jwt = verifier.verify(authHeader);
            //token 最多一天+2小时 过期，即今天白天登录获取token之后 第二天凌晨过期,
            //过期之后 需要重新登陆获取token
            if(jwt.getNotBefore().getTime()<System.currentTimeMillis()){
                //TODO 重定向到首页 暂时只打log
                log.error("userId:{},token expired",jwt.getClaims().get(AppConstant.USER_UUID).asString());
                throw new UserRemindException("token已过期,请重新登陆");
            }
            return jwt;
        } catch (JWTVerificationException e) {
            throw new UserRemindException("非法访问");
        }
    }

    public static String getUserUuid(HttpServletRequest request) {
        DecodedJWT jwt = verifyToken(request);
        return jwt.getClaims().get(AppConstant.USER_UUID).asString();

    }

}
