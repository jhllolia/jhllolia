package com.tosok.user.IamPort;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tosok.user.IamPortRequest.AuthData;
import com.tosok.user.IamPortRequest.CancelData;
import com.tosok.user.IamPortResponse.AccessToken;
import com.tosok.user.IamPortResponse.IamportResponse;
import com.tosok.user.IamPortResponse.Payment;

public class IamportClient {
	private static final String API_URL = "https://api.iamport.kr";

	private String api_key = null;
	private String api_secret = null;
	private HttpClient client = null;
	private Gson gson = new Gson();

    /* ========== 아임포트 API 생성 ========== */
	public IamportClient(String api_key, String api_secret) {
		this.api_key = api_key;
		this.api_secret = api_secret;
		this.client = HttpClientBuilder.create().build();
	}

    /* ========== 아임포트 인증 여부 ========== */
	private IamportResponse<AccessToken> getAuth() throws Exception {
		AuthData authData = new AuthData(api_key, api_secret);
		String authJsonData = gson.toJson(authData);

		try {

			HttpPost postRequest = new HttpPost(API_URL + "/users/getToken");
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection","keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setEntity(new StringEntity(authJsonData));

			HttpResponse response = client.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			Type listType = new TypeToken<IamportResponse<AccessToken>>(){}.getType();
			IamportResponse<AccessToken> auth = gson.fromJson(body, listType);

			return auth;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	} 

    /* ====================  ==================== */
	private String postRequest(String path, String token, StringEntity postData) throws URISyntaxException {
		try {

			HttpPost postRequest = new HttpPost(API_URL + path);
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection","keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.addHeader("Authorization", token);
			postRequest.setEntity(postData);

			HttpResponse response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			return body;
		} catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

		return null;
	}

    /* ====================  ==================== */
	private String getRequest(String path, String token) throws URISyntaxException {
		try {

			HttpGet getRequest = new HttpGet(API_URL + path);
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", token);

			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			return body;
		  } catch (ClientProtocolException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }

		return null;
	}

    /* ====================  ==================== */
	public String getToken() throws Exception {
		IamportResponse<AccessToken> auth = this.getAuth();

		if( auth != null) {
			String token = auth.getResponse().getToken();
			return token;
		}

		return null;
	}

    /* ==================== impUid ==================== */
	public IamportResponse<Payment> paymentByImpUid(String impUid) throws Exception {
		String token = this.getToken();

		if(token != null) {
			String path = "/payments/" + impUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);

			return payment;
		}

		return null;		
	}

    /* ==================== merchant_Uid ==================== */
	public IamportResponse<Payment> paymentByMerchantUid(String merchantUid) throws Exception {
		String token = this.getToken();
		
		if(token != null) {
			String path = "/payments/find/" + merchantUid;
			String response = this.getRequest(path, token);
			
			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);

			return payment;
		}

		return null;
	}

    /* ==================== 아임포트 결제 금액 취소 ==================== */
	public IamportResponse<Payment> cancelPayment(CancelData cancelData) throws Exception {
		String token = this.getToken();

		if(token != null) {
			String cancelJsonData = gson.toJson(cancelData);
			String response = this.postRequest("/payments/cancel", token, new StringEntity(cancelJsonData));
			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();

			IamportResponse<Payment> payment = gson.fromJson(response, listType);

			return payment;
		}

		return null;
	}

}
