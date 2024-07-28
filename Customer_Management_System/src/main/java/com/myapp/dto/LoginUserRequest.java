package com.myapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequest {

     
	private String email;
    private String password;
	public LoginUserRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
 
    
    
    
}