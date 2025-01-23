package kabdulinovmedet.pet.kz.resume.Security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Component
@PropertySource("classpath:security.properties")
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.user-subject}")
    private String userSubject;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private DecodedJWT decodedJWT;

    private Date expirationDate;

    public String generateToken(String email){
        expirationDate = Date.from(ZonedDateTime.now(TimeZone.getTimeZone("GMT+6").toZoneId()). plusMinutes(expirationTime).toInstant());
        return JWT.create()
                .withSubject(userSubject)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public void verifyToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(userSubject)
                .withIssuer(issuer)
                .build();
        decodedJWT = verifier.verify(token);
    }

    public String getCliam(String claim){   
        return decodedJWT.getClaim(claim).asString();
    }
}
