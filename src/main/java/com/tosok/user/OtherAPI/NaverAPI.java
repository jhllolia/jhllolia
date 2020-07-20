package com.tosok.user.OtherAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component("NaverAPI")
public class NaverAPI {
	protected Log log = LogFactory.getLog(NaverAPI.class);

	/* ========== 네이버 로그인 토큰 생성 ========== */
	public String getAccessToken(String client_Id) {
		String access_Token = "";

		try {
			/* ========== 네이버 로그인 URL 경로 출력  ========== */
		    String redirectURI = URLEncoder.encode("http://localhost:8080/member/login", "UTF-8");
		    SecureRandom random = new SecureRandom();
		    String state = new BigInteger(130, random).toString();

		    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		    apiURL += "&client_id=" + client_Id;
		    apiURL += "&redirect_uri=" + redirectURI;
		    apiURL += "&state=" + state;

		    access_Token = apiURL;
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("getAccessToken : " + access_Token);
		return access_Token;
	}

	/* ========== 네이버 로그인 ========== */
	public String getAccessCode(String client_Id, String client_Secret, HttpServletRequest request) {
		String access_Token = "";
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");

		try {

			String redirectURI = URLEncoder.encode("http://localhost:8080/info/info_payment", "UTF-8");
			String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";

		    apiURL += "client_id=" + client_Id;
		    apiURL += "&client_secret=" + client_Secret;
		    apiURL += "&redirect_uri=" + redirectURI;
		    apiURL += "&code=" + code;
		    apiURL += "&state=" + state;

		    URL url = new URL(apiURL);
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		    conn.setRequestMethod("GET");

			/* ========== 결과 코드가 200이라면 성공 ========== */
			int responseCode = conn.getResponseCode();

			/* ========== 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기 ========== */
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();

        	br.close();
		} catch (Exception e) {	
			e.printStackTrace();
		}

		return access_Token;
	}

	/* ========== 사용자 정보 출력 ========== */
	public HashMap<String, Object> getUserInfo(String code) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String apiURL = "https://openapi.naver.com/v1/nid/me";

		try {

			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + code);

			int responseCode = conn.getResponseCode();

			if(responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}

				/* ========== Json Data ========== */
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);
				JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();		// Json 출력

				/* ========== 사용자 정보 출력 ========== */
				userInfo.put("id", response.getAsJsonObject().get("id").getAsString());					// Naver Num
				userInfo.put("profile", response.getAsJsonObject().get("profile_image").getAsString());	// 프로필
				userInfo.put("nickname", response.getAsJsonObject().get("name").getAsString());			// 닉네임
				userInfo.put("email", response.getAsJsonObject().get("email").getAsString());			// 이메일
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userInfo;
	}

}
