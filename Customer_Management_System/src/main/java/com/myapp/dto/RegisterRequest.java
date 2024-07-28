package com.myapp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
public class RegisterRequest {

	    private String firstName;
	    private String lastName;
	    private String street;
	    private String address;
	    private String city;
	    private String state;
	    private String email;
	    private String phone;

        private String password;


}