package com.tosok.user.IamPortRequest;

import com.google.gson.annotations.SerializedName;

public class AuthData {

	@SerializedName("imp_key")
	private String api_key;				// 아임포트 KEY
	
	@SerializedName("imp_secret")
	private String api_secret;			// 아임포트 Secret
	
	public AuthData(String api_key, String api_secret) {
		this.api_key = api_key;
		this.api_secret = api_secret;
	}

}