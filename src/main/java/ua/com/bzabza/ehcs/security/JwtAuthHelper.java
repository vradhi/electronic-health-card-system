package ua.com.bzabza.ehcs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

class JwtAuthHelper {

    private static final String ISSUER = "ElectronicHealthCardSystem";

    private static final String SIGNING_KEY = "^@()@^($%*$%23456(((";

    public static final String CLAIM_USER_ID = "userId";

    public static final String ROLES = "roles";

    static String createJwt(Integer userId, Collection<String> roles) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SIGNING_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(CLAIM_USER_ID, userId)
                    .withArrayClaim(ROLES, roles.toArray(new String[roles.size()]))
//                    .withExpiresAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            throw new IllegalStateException(exception);
        }
    }

    static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(token);
        } catch (UnsupportedEncodingException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
