package com.tosok.user.OtherAPI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component("KakaoAPI")
public class KakaoAPI {
	protected Log log = LogFactory.getLog(KakaoAPI.class);

	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {

			String client_id = "8e17e941ff8ec2fc7c002f7273b50ae2";				// 클라이언트 KEY	
			String redirect_uri = "http://localhost:8080/kakao/login";		// REDIRECT 경로

			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			/* ========== POST 요청을 위해 기본값이 false인 setDoOutput을 true로  ========== */
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			/* ========== POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송  ========== */
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=" + client_id);
			sb.append("&redirect_uri=" + redirect_uri);
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			/* ========== 결과 코드가 200이라면 성공  ========== */
			int responseCode = conn.getResponseCode();

			if(responseCode == 200) {
				/* ========== 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기  ========== */
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}

				log.info("getAccessToken : " + result);

				/* ========== Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성  ========== */
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);

				access_Token = element.getAsJsonObject().get("access_token").getAsString();

	        	br.close();
	        	bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	}

	public HashMap<String, Object> getUserInfo (String access_Token) {
		/* ========== 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언  ========== */
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";

		try {

			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			/* ========== 요청에 필요한 Header에 포함될 내용  ========== */
			conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();

			if(responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}

				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);

				JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();			// Json 파싱
				JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();	// Json 파싱

				/* ==================== 회원정보 출력  ==================== */
				String nickname = properties.getAsJsonObject().get("nickname").getAsString();
				String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
				String email = kakao_account.getAsJsonObject().get("email").getAsString();

				userInfo.put("code", responseCode);			// 출력 결과
				userInfo.put("profile", profile_image);		// 프로필
				userInfo.put("nickname", nickname);			// 닉네임
				userInfo.put("email", email);				// 메일
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userInfo;
	}

/*
	카카오 로그아웃 => 세션을 종료하면 자동으로 카카오 로그인 종료

	public void kakaoLogout(String access_Token) {
		if(access_Token == null || !access_Token.equals("")) {

			try {

				String reqURL = "https://kapi.kakao.com/v1/user/logout";

				URL url = new URL(reqURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Authorization", "Bearer " + access_Token);
				
				int responseCode = conn.getResponseCode();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
		        String result = "";
		        String line = "";
		        
		        while ((line = br.readLine()) != null) {
		            result += line;
		        }

				log.info("kakaoLogout : " + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			log.info("kakaoLogout None");
		}
	}
*/

}
