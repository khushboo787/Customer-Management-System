package com.myapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationTokenResponse {

	private String message;
    private final String token;
    private final String type = "Bearer ";
    private final String instruction = "Handle error!";
   

    public AuthenticationTokenResponse(String msg, String token) {
    	this.message=msg;
        this.token = token;
    }
}