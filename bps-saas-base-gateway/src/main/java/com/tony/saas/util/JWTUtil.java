package com.tony.saas.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * JWT帮助类
 */
@Slf4j
public class JWTUtil {

    /**
     * jwt
     * header:
     * {
     *	'typ': 'JWT',//声明类型
     *	'alg': 'HS256'//声明加密的算法 通常直接使用 HMAC SHA256
     *	}
     * payload:包含公共申明和私密申明
     * 	iss: jwt签发者
     *	sub: jwt所面向的用户
     *	aud: 接收jwt的一方
     *	exp: jwt的过期时间，这个过期时间必须要大于签发时间
     *	nbf: 定义在什么时间之前，该jwt都是不可用的.
     *	iat: jwt的签发时间
     *	jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     * 公共的声明 ：
     * 公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.但不建议添加敏感信息，因为该部分在客户端可解密.
     * 私有的声明 ：
     * 私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为明文信息。
     *
     */

    private static PropertyUtil propertiesLoader = new PropertyUtil( "classpath:security.properties" );

    // 默认加密算法
    private static SignatureAlgorithm defaultAlg = SignatureAlgorithm.HS256;

    // 生成key
    private static Key generatorKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary( propertiesLoader.getProperty( "jwt.secret" ) );
        return new SecretKeySpec( apiKeySecretBytes, defaultAlg.getJcaName() );
    }

    /**
     *
     * @param subject 用户信息jsonStr
     * @return
     */
    public static String createToken( String subject ) {
        // 有效时间
        long ttlMillis = Long.parseLong( propertiesLoader.getProperty( "jwt.ttl" ) );
        // 刷新时间
        long refreshTtlMillis = Long.parseLong( propertiesLoader.getProperty( "jwt.refresh.ttl" ) );

        boolean autoRefresh = propertiesLoader.getBoolean( "jwt.refresh.autore" );
        // jwt
        JwtBuilder builder = Jwts.builder()
                .setId( propertiesLoader.getProperty( "jwt.id" ) )
                .setIssuedAt( DateUtil.getCurrentDate() );

        // 用户自定义信息
        if( subject != null ) {
            builder.setSubject( subject );
        }
        // 发布单位
        builder.setIssuer( propertiesLoader.getProperty( "jwt.iss" ) );
        // 签名
        builder.signWith( defaultAlg, generatorKey() );
        // 刷新有效时间
        if( autoRefresh&&refreshTtlMillis > 0 ) {
            builder.claim( "refreshTime", DateUtil.addMillisToCurrentDate( refreshTtlMillis ) );
        }
        // 失效时间
        if( ttlMillis > 0 ) {
            builder.setExpiration( DateUtil.addMillisToCurrentDate( ttlMillis ) );
        }
        return builder.compact();
    }

    /**
     * 检查token是否失效
     * @return
     */
    public static boolean validateTokenEffective(String oldToken) {
        boolean rs = true;
        try {
            Claims cla = parseToken( oldToken );
            Long expiration = cla.getExpiration().getTime();
            Long now = DateUtil.getCurrentTimeMillis();
            rs = expiration<now;
        } catch( Exception e ) {
            log.error( e.getMessage() );
        }
        return rs;
    }


    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims parseToken( String token ) {
        return Jwts.parser().setSigningKey( DatatypeConverter.parseBase64Binary( propertiesLoader.getProperty( "jwt.secret" ))  ).parseClaimsJws( token ).getBody();
    }



}
