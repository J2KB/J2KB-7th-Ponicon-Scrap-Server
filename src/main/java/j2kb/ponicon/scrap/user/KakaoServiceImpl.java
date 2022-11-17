package j2kb.ponicon.scrap.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.UserInfo;
import j2kb.ponicon.scrap.utils.ICookieService;
import j2kb.ponicon.scrap.utils.IJwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.KAKAO_GET_USER_INFO_FAIL;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoServiceImpl implements IKakaoService {

    private final IJwtService jwtService;
    private final ICookieService cookieService;
    private final UserRepository userRepository;
    private final ISocialUserService userService;

    @Value("${server.host.api}")
    private String frontHost;
    private String redirectionUrl;
    private final String userInfoHost = "https://kapi.kakao.com/v2/user/me";

    private class KaKaoUser{


        private String kakaoId; // 아이디
        private String name;

        protected KaKaoUser(String id, String name){
            this.kakaoId = id;
            this.name = name;
        }

        public String getKakaoId() {
            return kakaoId;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    // 카카오 로그인
    public LoginRes login(String KakaoAccessToken, HttpServletResponse response){

        this.redirectionUrl  = frontHost + "/user/login/kakao";

        // user 조회
        UserInfo userInfo = getUser(KakaoAccessToken);

        // 토큰 발급
        String accessToken = jwtService.createAccessToken(userInfo.getEmail());
        String refreshToken = jwtService.createRefreshToken(userInfo.getEmail());

        // 쿠키 발급
        Cookie accessCookie = cookieService.createAccessCookie(accessToken, true);
        Cookie refreshCookie = cookieService.createRefreshCookie(refreshToken, true);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return new LoginRes(userInfo.getId());
    }

    // 카카오로부터 user 조회
    private UserInfo getUser(String accessToken){

        KaKaoUser kakaoUser = getUserInfo(accessToken);

        Long userId = userService.checkUserHasJoin(kakaoUser.getKakaoId());
        // 해당하는 사용자가 없으면 자동으로 회원가입 진행
        if(userId == -1L){
            log.info("회원가입이 되지 않은 유저의 카카오 로그인 발생. 회원가입 진행: idx={}", kakaoUser.getKakaoId());
            return userService.joinBySocial(kakaoUser.getKakaoId(), kakaoUser.getName());
        }

        return new UserInfo(userId, kakaoUser.getKakaoId(), kakaoUser.getName());
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
            log.trace("responseCode : {}" , responseCode);

            // 200 아닐경우 예외처리 필요
            if(responseCode != HttpStatus.OK.value()){
                throw new BaseException(KAKAO_GET_USER_INFO_FAIL);
            }

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.trace("response body : {}", result);

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
        } catch (BaseException e){
            log.error("카카오 로그인 중 예상치못한 에러: {}", e.getMessage());
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("카카오 로그인 중 예상치못한 에러: {}", KAKAO_GET_USER_INFO_FAIL.getMessage());
            throw new BaseException(KAKAO_GET_USER_INFO_FAIL);
        }
    }


}
