package j2kb.ponicon.scrap.utils;

import io.jsonwebtoken.*;
import j2kb.ponicon.scrap.response.AuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.JWT_TOKEN_EXPIRE;
import static j2kb.ponicon.scrap.response.BaseExceptionStatus.JWT_TOKEN_INVALID;
import static j2kb.ponicon.scrap.utils.JwtData.*;

/**
 * JWT 관련 서비스 코드
 */
@Service
@Slf4j
public class JwtServiceImpl implements IJwtService{

    // 엑세스토큰 생성
    public String createAccessToken(String email){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 타입 지정. Header.TYPE="type", Header.JWT_TYPE="jwt"
                .setIssuer("scrap.hana-umc.shop") //토큰 발급자 설정 (iss)
                .setIssuedAt(now) //발급 시간 설정 (iat) Date 타입만 추가 가능.
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_MILLIS)) //만료시간 설정 (exp). Date 타입만 추가 가능.
                .setSubject(email) //비공개 클래임을 설정할 수 있음. key-value
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY) //해싱 알고리즘과 시크릿 key 설정
                .compact(); //jwt 토큰 생성
    }

    // 리프레시토큰 생성
    public String createRefreshToken(String email){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 타입 지정. Header.TYPE="type", Header.JWT_TYPE="jwt"
                .setIssuer("hana-umc.shop") //토큰 발급자 설정 (iss)
                .setIssuedAt(now) //발급 시간 설정 (iat) Date 타입만 추가 가능.
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_MILLIS)) //만료시간 설정 (exp). Date 타입만 추가 가능.
                .setSubject(email) //비공개 클래임을 설정할 수 있음. key-value
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY) //해싱 알고리즘과 시크릿 key 설정
                .compact(); //jwt 토큰 생성
    }

    // jwt 유효 확인
    public Jws<Claims> validationAndGetJwt(String jwtToken){
        Jws<Claims> claims;

        try{
            claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(jwtToken);

            return claims;
        } catch (ExpiredJwtException e){ //시간 만료
            log.info("jwt 토큰 에러: {}, token={}", JWT_TOKEN_EXPIRE.getMessage(), jwtToken);
            throw new AuthorizationException(JWT_TOKEN_EXPIRE);
        } catch (Exception ignored) {
            log.info("jwt 토큰 에러: {}, token={}", JWT_TOKEN_INVALID.getMessage(), jwtToken);
            throw new AuthorizationException(JWT_TOKEN_INVALID);
        }
    }

    // jwt에서 subject(username) 얻기
    public String getEmailByJwt(String jwtToken){
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(jwtToken);

            return claims.getBody().getSubject();

        } catch (ExpiredJwtException e){ //시간 만료
            log.info("jwt 토큰 에러: {}, token={}", JWT_TOKEN_EXPIRE.getMessage(), jwtToken);
            throw new AuthorizationException(JWT_TOKEN_EXPIRE);
        } catch (Exception ignored) {
            log.info("jwt 토큰 에러: {}, token={}", JWT_TOKEN_INVALID.getMessage(), jwtToken);
            throw new AuthorizationException(JWT_TOKEN_INVALID);
        }
    }

    // jwt에서 subject(username) 얻기
    public String getEmailByJwt(Jws<Claims> claims){
        return claims.getBody().getSubject();
    }
}
