package j2kb.ponicon.scrap.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.utils.CookieService;
import j2kb.ponicon.scrap.utils.JwtService;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.KAKAO_GET_TOKEN_FAIL;
import static j2kb.ponicon.scrap.response.BaseExceptionStatus.KAKAO_GET_USER_INFO_FAIL;
import static j2kb.ponicon.scrap.utils.JwtData.KAKAO_REST_API_KEY;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {

    private final JwtService jwtService;
    private final CookieService cookieService;
    private final UserRepository userRepository;

    private final String tokenHost = "https://kauth.kakao.com/oauth/token";
    private final String userInfoHost = "https://kapi.kakao.com/v2/user/me";
    private final String redirectionUrl = "http://localhost:8081/user/login/kakao";

    private class Token{

        private String accessToken;
        private String refreshToken;

        protected Token(String access, String refresh){
            this.accessToken = access;
            this.refreshToken = refresh;
        }

        protected String getAccessToken() {
            return accessToken;
        }

        protected String getRefreshToken() {
            return refreshToken;
        }
    }

    private class KaKaoUser{

        private String id; // 아이디
        private String name;

        protected KaKaoUser(String id, String name){
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    // 카카오 로그인
    public User login(String code, HttpServletResponse response){

        // 인가코드로 카카오의 토큰(access, refresh) 발급받기
        Token token = getTokens(code);
        System.out.println("token.getAccessToken() = " + token.getAccessToken());

        // user 조회
        User user = getUser(token.getAccessToken());

        // 토큰 발급
        String accessToken = jwtService.createAccessToken(user.getUsername());
        String refreshToken = jwtService.createRefreshToken(user.getUsername());

        // 쿠키 발급
        Cookie accessCookie = cookieService.createAccessCookie(accessToken, true);
        response.addCookie(accessCookie);

        return user;
    }

    // 카카오의 access토큰과 refresh토큰 받기
    private Token getTokens(String code){
        try{
            URL url = new URL(tokenHost);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+KAKAO_REST_API_KEY);
            sb.append("&redirect_uri="+redirectionUrl);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            System.out.println(conn.getResponseMessage());

            // 200 아닐경우 예외처리 필요

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // 응답 바디에서 토큰값 읽어오기
            String accessToken = element.getAsJsonObject().get("access_token").getAsString();
            String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            Token token = new Token(accessToken, refreshToken);

            br.close();
            bw.close();

            return token;
        }catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(KAKAO_GET_TOKEN_FAIL);
        }
    }

    // 사용자 정보 가져오기
    private KaKaoUser getUserInfo(String accessToken){
        try{
            URL url = new URL(userInfoHost);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setDoOutput(true); // 서버로부터 받아오는 값이 있다면 ture

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 200 아닐경우 예외처리 필요

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // 응답바디에서 사용자 정보 꺼내오기
            String id = element.getAsJsonObject().get("id").toString();
            String name = element.getAsJsonObject().getAsJsonObject("properties").get("nickname").toString();
            name = name.replace("\"", ""); // "홍길동" -> 홍길동

            KaKaoUser kaKaoUser = new KaKaoUser(id, name);

            br.close();

            return kaKaoUser;
        }catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(KAKAO_GET_USER_INFO_FAIL);
        }
    }

    // user 조회
    private User getUser(String accessToken){

        KaKaoUser kakaoUser = getUserInfo(accessToken);

        User user = userRepository.findByUsername(kakaoUser.getId());

        // 해당하는 사용자가 없으면 자동으로 회원가입 진행
        if(user == null){
            user = join(kakaoUser.getId(), kakaoUser.getName());
        }

        return user;
    }

    // 회원가입
    private User join(String username, String name){

        User user = new User(username, username, name);
        userRepository.save(user);
        return user;
    }
}
