package j2kb.ponicon.scrap.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import j2kb.ponicon.scrap.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.KAKAO_GET_TOKEN_FAIL;
import static j2kb.ponicon.scrap.utils.JwtData.KAKAO_REST_API_KEY;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {

    private final UserRepository userRepository;

    private final String tokenHost = "https://kauth.kakao.com/oauth/token";
    private final String userInfoHost = "https://kapi.kakao.com/v2/user/me";

    public void login(String code){

        Map<String, String> tokenMap = getTokens(code);

        for(String key : tokenMap.keySet()){
            System.out.println("tokenMap.get(key) = " + tokenMap.get(key));
        }

        getUser(tokenMap.get("accessToken"));
        getUserInfo(tokenMap.get("accessToken"));

    }

    private Map<String, String> getTokens(String code){
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
            sb.append("&redirect_uri=http://localhost:8081/user/login/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

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
//            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String accessToken = element.getAsJsonObject().get("access_token").getAsString();
            String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            Map<String, String> tokenMap = Collections.synchronizedMap(new HashMap());
            tokenMap.put("accessToken", accessToken);
            tokenMap.put("refreshToken", refreshToken);

            br.close();
            bw.close();

            return tokenMap;
        }catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(KAKAO_GET_TOKEN_FAIL);
        }
    }

    private Map<String, String> getUserInfo(String accessToken){
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

            String id = element.getAsJsonObject().get("id").toString();
            String name = element.getAsJsonObject().getAsJsonObject("properties").get("nickname").toString();
            name = name.replace("\"", "");

            Map<String, String> userInfoMap = Collections.synchronizedMap(new HashMap());

            userInfoMap.put("id", id);
            userInfoMap.put("name", name);

//            System.out.println("id = " + id);
//            System.out.println("name = " + name);

            br.close();

            return userInfoMap;
        }catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(KAKAO_GET_TOKEN_FAIL);
        }
    }

    public void getUser(String accessToken){
        Map<String, String> userInfoMap = getUserInfo(accessToken);


    }

    private void join(){

    }
}
