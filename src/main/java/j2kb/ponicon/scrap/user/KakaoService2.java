package j2kb.ponicon.scrap.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.utils.ICookieService;
import j2kb.ponicon.scrap.utils.IJwtService;
import lombok.RequiredArgsConstructor;
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
public class KakaoService2 implements IKakaoService2{

    private final IJwtService jwtService;
    private final ICookieService cookieService;
    private final UserRepository userRepository;
    private final IUserService userService;

    @Value("${server.host.api}")
    private String frontHost;
    private String redirectionUrl;
    private final String userInfoHost = "https://kapi.kakao.com/v2/user/me";

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

    @Override
    // 카카오 로그인
    public User login(String KakaoAccessToken, HttpServletResponse response){

        this.redirectionUrl  = frontHost + "/user/login/kakao";

        // user 조회
        User user = getUser(KakaoAccessToken);

        // 토큰 발급
        String accessToken = jwtService.createAccessToken(user.getUsername());
        String refreshToken = jwtService.createRefreshToken(user.getUsername());

        // 쿠키 발급
        Cookie accessCookie = cookieService.createAccessCookie(accessToken, true);
        Cookie refreshCookie = cookieService.createRefreshCookie(refreshToken, true);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return user;
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
        } catch (BaseException e){
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(KAKAO_GET_USER_INFO_FAIL);
        }
    }

    // 카카오로부터 user 조회
    private User getUser(String accessToken){

        KaKaoUser kakaoUser = getUserInfo(accessToken);

        User user = userRepository.findByUsername(kakaoUser.getId());

        // 해당하는 사용자가 없으면 자동으로 회원가입 진행
        if(user == null){
            user = userService.joinBySocial(kakaoUser.getId(), kakaoUser.getName());
        }

        return user;
    }
}