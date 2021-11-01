package com.mindata.challenge.component;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mindata.challenge.entity.User;
import com.mindata.challenge.exceptionhandling.AuthJWTException;

@Component
public class JwtProvider {


	public static final String AUTHORIZATION = "Authorization";
	@Value("${jwt.expiration}")
	public static final int TTLTOKEN = 900;
	@Value("${jwt.secret}")
	private static final String SECRET = "sigkey";


	public String authUSer(User user) {
		// MOCK
		if (!user.getUserName().strip().equals("anepa") || !user.getPassword().strip().equals("1234qwer")) {
			throw new AuthJWTException("Error de autenticacion...");
		}

		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		// Creo fecha Actual
		Date issuedAtDate = new Date();
		// Fecha de Expiracion
		Date expirationDate = getExpirationDate(TTLTOKEN);
		// Creo Token string
		String token = createToken("MinData",
				expirationDate,
				issuedAtDate,
				user.getUserName(),
				algorithm);

		return token;
	}

	public String createToken(String issuer, Date expirationDate, Date issuedAtDate, String user, Algorithm algorithm) {
		StringBuilder stBuilder = new StringBuilder();
		stBuilder.append("Bearer ");

		String token = JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(expirationDate)
				.withIssuedAt(issuedAtDate)
				.withSubject(user)
				.sign(algorithm);

		return stBuilder.append(token).toString();
	}

	public DecodedJWT validoTokenJWT(String token)  {
		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}

	public Date getExpirationDate(Integer ttl) {
		Date expirationDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(expirationDate); 
		calendar.add(Calendar.SECOND, ttl);
		return calendar.getTime();
	}
	
	public String getToken(String headerRequest){
		if(headerRequest != null && headerRequest.startsWith("Bearer")) {
			return headerRequest.replace("Bearer ", "");
		}
		return null;
	}

	public String refreshToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		// Verifico que el tkn sea valido
		DecodedJWT jwt = validoTokenJWT(getToken(token));
		// Creo fecha de expiracion
		Date expirationDate = getExpirationDate(TTLTOKEN);
		// Creo Token con fecha de expiracion actualizada.
		return createToken(jwt.getIssuer(),
				expirationDate,
				jwt.getIssuedAt(),
				jwt.getSubject(),
				algorithm);
	}
}
