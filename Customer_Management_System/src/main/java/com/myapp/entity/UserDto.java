package com.myapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	    private String firstName;
	    private String lastName;
	    private String street;
	    private String address;
	    private String city;
	    private String state;
	    private String email;
	    private String phone;
        private String role;
	
}