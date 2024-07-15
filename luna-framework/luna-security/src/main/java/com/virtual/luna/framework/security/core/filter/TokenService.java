package com.virtual.luna.framework.security.core.filter;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.framework.security.core.user.LoginUser;
import com.virtual.luna.framework.security.properties.SecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * token验证处理
 *
 * @author shi
 */
@Component
public class TokenService {

    @Resource
    private SecurityProperties securityProperties;


    private String createToken(Map<String, Object> claims) {
        try {
            // 创建 JWT Claims
            JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
            claims.forEach(claimsSetBuilder::claim);

            JWTClaimsSet claimsSet = claimsSetBuilder.build();

            // 创建签名器
            JWSSigner signer = new MACSigner(securityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));

            // 创建 JWT
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS512),
                    claimsSet
            );

            // 签名
            signedJWT.sign(signer);

            // 返回 JWT
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error creating JWT token", e);
        }
    }


    private Map<String, Object> parseToken(String token) {
        try {
            // 解析 JWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // 验证签名
            JWSVerifier verifier = new MACVerifier(securityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));

            if (signedJWT.verify(verifier)) {
                // 获取 JWT Claims
                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                return claimsSet.getClaims();
            } else {
                throw new RuntimeException("JWT signature verification failed");
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据 token 构建 LoginUser 对象
     *
     * @param token JWT 令牌
     * @return LoginUser 对象
     */
    public LoginUser buildLoginUserByToken(String token) {
        try {

            Map<String, Object> map = parseToken(token);

            if(map == null){
                return null;
            }

            LoginUser loginUser = new LoginUser();

            loginUser.setUserId(Long.valueOf((String) map.get("id")));
            loginUser.setExpiration((Date) map.get("exp"));
            loginUser.setScopes((List<String>) map.get("scope"));
            loginUser.setResource((List<String>) map.get("resource"));
            return loginUser;
        } catch (Exception e) {

            return null;
        }
    }

}
