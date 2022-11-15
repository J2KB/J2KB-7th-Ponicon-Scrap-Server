package j2kb.ponicon.scrap.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface IJwtService {

    public String createAccessToken(String email);
    public String createRefreshToken(String email);
    public Jws<Claims> validationAndGetJwt(String jwtToken);
    public String getEmailByJwt(String jwtToken);
    public String getEmailByJwt(Jws<Claims> claims);

}
