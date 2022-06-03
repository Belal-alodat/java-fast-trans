package com.odat.fastrans.security;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    
    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

	/*
	 * @Value("${jwt.signing.key}") public String SIGNING_KEY;
	 */

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;
//
//    public String generateTokenX(Authentication authentication) throws IllegalArgumentException, JWTCreationException {
//    	
//    	String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//    	
//        return JWT.create()
//                .withSubject("User Details")
//                .withClaim("email", authentication.getName())
//                .withIssuedAt(new Date())
//                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
//                .withExpiresAt(new  Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
//                
//                .sign(Algorithm.HMAC256(secret));
//        
//		/*
//		 * return Jwts.builder() .setSubject(authentication.getName())
//		 * .claim(AUTHORITIES_KEY, authorities) .setIssuedAt(new
//		 * Date(System.currentTimeMillis())) .setExpiration(new
//		 * Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
//		 * .signWith(SignatureAlgorithm.HS256, SIGNING_KEY) .compact();
//		 */
//    }

	public String validateTokenAndRetrieveSubject(String token)/* throws JWTVerificationException */ {
        
//    	JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
//                .withSubject("User Details")
//                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
//                .build();
//        DecodedJWT jwt = verifier.verify(token);
//        return jwt.getClaim("email").asString();
        return "";
    }

}