package j2kb.ponicon.scrap.utils;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

import static j2kb.ponicon.scrap.utils.JwtData.*;

/**
 * JWT 관련 서비스 코드
 */
@Service
public class JwtServiceImpl implements IJwtService{

    // 엑세스토큰 생성
    public String createAccessToken(String username){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 타입 지정. Header.TYPE="type", Header.JWT_TYPE="jwt"
                .setIssuer("scrap.hana-umc.shop") //토큰 발급자 설정 (iss)
                .setIssuedAt(now) //발급 시간 설정 (iat) Date 타입만 추가 가능.
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_MILLIS)) //만료시간 설정 (exp). Date 타입만 추가 가능.
                .setSubject(username) //비공개 클래임을 설정할 수 있음. key-value
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY) //해싱 알고리즘과 시크릿 key 설정
                .compact(); //jwt 토큰 생성
    }

    // 리프레시토큰 생성
    public String createRefreshToken(String username){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 타입 지정. Header.TYPE="type", Header.JWT_TYPE="jwt"
                .setIssuer("hana-umc.shop") //토큰 발급자 설정 (iss)
                .setIssuedAt(now) //발급 시간 설정 (iat) Date 타입만 추가 가능.
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_MILLIS)) //만료시간 설정 (exp). Date 타입만 추가 가능.
                .setSubject(username) //비공개 클래임을 설정할 수 있음. key-value
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY) //해싱 알고리즘과 시크릿 key 설정
                .compact(); //jwt 토큰 생성
    }
}
