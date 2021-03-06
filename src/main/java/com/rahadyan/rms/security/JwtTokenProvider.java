package com.rahadyan.rms.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 
 * @author Rahadyan_W995
 *
 */

@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	/**
	 * Secret key for the specified algorithm to produce a JWS the value is set in application.properties
	 */
	@Value("${app.jwtSecret}")
	private String jwtSecret;

	/**
	 * Used for logged in expiration time the value is set in application.properties
	 */
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	/**
	 * This method generate token for user when user logged in set subject with the userPrincipal id set issued time as 
	 * userPrincipal id set issued time as now set expiration by now +jwtExpirationInMs set signWith with HSS12 
	 * jwtExpirationInMs set signWith with HSS12 algorithm and jwtSecret for thesecret key builds the JWT and 
	 * serializes it to a compact
	 * 
	 * @param authentication
	 */
	public String generateToken(Authentication authentication) {

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * This method get user id from token set secret key and token and get body (user id)
	 * 
	 * @param token 
	 * 
	 * @return claims that parsed into id by token and secret key
	 */
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}