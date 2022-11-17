package j2kb.ponicon.scrap.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface IJwtService {

    /**
     * 유저 아이디로 access토큰 발급
     * @param email
     * @return access토큰
     */
    public String createAccessToken(String email);

    /**
     * 유저 아이디로 refresh토큰 발급
     * @param email
     * @return refresh토큰
     */
    public String createRefreshToken(String email);

    /**
     * jwt 토큰 유효성 검증하고 jwt값 리턴해줌
     * @param jwtToken
     * @return Jws<Claims>
     */
    public Jws<Claims> validationAndGetJwt(String jwtToken);

    /**
     * jwt트콘으로 유저 아이디 얻어오기
     * @param jwtToken
     * @return 유저 아이디
     */
    public String getEmailByJwt(String jwtToken);

    /**
     * jwt트콘으로 유저 아이디 얻어오기
     * @param claims
     * @return 유저 아이디
     */
    public String getEmailByJwt(Jws<Claims> claims);

}
