package j2kb.ponicon.scrap.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static j2kb.ponicon.scrap.utils.JwtData.KAKAO_REST_API_KEY;

@Service
@Transactional
public class KakaoService {

    private final String tokenHost = "https://kauth.kakao.com/oauth/token";

    public void login(String code){

        Map<String, String> tokenMap = getToken(code);

//        for(String key : tokenMap.keySet()){
//            System.out.println("tokenMap.get(key) = " + tokenMap.get(key));
//        }

    }

    private Map<String, String> getToken(String code){
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
            sb.append("&client_id=3fa8dfba8527a268e4a41f26278b4d8d");
            sb.append("&redirect_uri=http://localhost:8081/user/login/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseMessage = " + conn.getResponseMessage());
            System.out.println("responseCode : " + responseCode);
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
        }

        return null;
    }
}
