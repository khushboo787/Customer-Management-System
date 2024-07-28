package com.myapp.service;

 
 
import javax.management.relation.RoleNotFoundException;

import org.springframework.security.authentication.BadCredentialsException;

import com.myapp.dto.AuthenticationTokenResponse;
import com.myapp.dto.LoginUserRequest;
import com.myapp.dto.RegisterRequest;
import com.myapp.dto.UserRegisterResponse;
import com.myapp.entity.Role;
import com.myapp.entity.UserDto;
import com.myapp.exception.UserNotFoundException;
import com.myapp.exception.UserRegistrationFailedException;

public interface AuthenticationService {

    UserRegisterResponse registerNewUserService(RegisterRequest userRegisterRequest)throws UserRegistrationFailedException;
    
    AuthenticationTokenResponse loginUserService(LoginUserRequest loginUserRequest)throws BadCredentialsException;
    
    UserDto getUserByEmail(String email) throws UserNotFoundException;
    
    Role addRole(Role role) throws RoleNotFoundException;
}