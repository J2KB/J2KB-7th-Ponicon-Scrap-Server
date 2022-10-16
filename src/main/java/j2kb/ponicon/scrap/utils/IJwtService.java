package j2kb.ponicon.scrap.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface IJwtService {

    public String createAccessToken(String username);
    public String createRefreshToken(String username);
    public Jws<Claims> validationAndGetJwt(String jwtToken);
    public String getUsernameByJwt(String jwtToken);
    public String getUsernameByJwt(Jws<Claims> claims);

}
