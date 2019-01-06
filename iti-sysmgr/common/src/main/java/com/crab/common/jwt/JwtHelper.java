package com.crab.common.jwt;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.crab.common.Constant;
import com.crab.common.vo.AccessToken;
import com.crab.common.vo.Audience;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {
	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	public static String createJWT(String name, String userId, String role, String audience, String issuer,
			long TTLMillis, String base64Security) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(name)
				.setHeaderParam("typ", "JWT")
				.claim("role", role)
				.claim("unique_name", name)
				.claim("userid", userId) // 自定义属性
				.setIssuer(issuer) // 签发者
				.setAudience(audience) // 接受者
				.signWith(signatureAlgorithm, signingKey); // 签名算法以及密匙
		// 添加Token过期时间
		if (TTLMillis >= 0) {
			long expMillis = nowMillis + TTLMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}

		// 生成JWT
		return builder.compact();
	}

	/**
	 * 返回token
	 */
	public static AccessToken accessToken(String name, String id, String role, Audience audience) {
		// 拼装accessToken
		String accessToken = JwtHelper.createJWT(name, id,
				role, audience.getClientId(), audience.getName(),
				audience.getExpiresSecond() * 1000, audience.getBase64Secret());

		// 返回accessToken
		AccessToken accessTokenEntity = new AccessToken();
		accessTokenEntity.setToken(accessToken);
		accessTokenEntity.setExpires_in(audience.getExpiresSecond());
		accessTokenEntity.setBearer(Constant.Bearer);//持票人
		return accessTokenEntity;
	}
}