package com.myapp.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import javax.management.relation.RoleNotFoundException;

import com.myapp.config.AuthenticationUserDetailsService;
import com.myapp.config.JwtAuthenticationUtils;
import com.myapp.dto.AuthenticationTokenResponse;
import com.myapp.dto.LoginUserRequest;
import com.myapp.dto.RegisterRequest;
import com.myapp.dto.UserRegisterResponse;
import com.myapp.entity.Role;
import com.myapp.entity.RoleEnum;
import com.myapp.entity.UserDto;
import com.myapp.entity.UserModel;
import com.myapp.exception.UserNotFoundException;
import com.myapp.exception.UserRegistrationFailedException;
import com.myapp.repository.RoleRepository;
import com.myapp.repository.UserModelRepository;
import jakarta.transaction.Transactional;



@Service 
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserModelRepository userModelRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationUtils jwtAuthenticationUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    
 
    public AuthenticationServiceImpl(UserModelRepository userModelRepository,
                                     AuthenticationUserDetailsService authenticationUserDetailsService,
                                     AuthenticationManager authenticationManager, JwtAuthenticationUtils jwtAuthenticationUtils,
                                     PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userModelRepository = userModelRepository;
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationUtils = jwtAuthenticationUtils;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRegisterResponse registerNewUserService(RegisterRequest userRegisterRequest)
            throws UserRegistrationFailedException {

        if (userModelRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new UserRegistrationFailedException("This Email already exists in the database");
        }

        UserModel userModel = new UserModel();
       
        userModel.setFirstName(userRegisterRequest.getFirstName());
        userModel.setLastName(userRegisterRequest.getLastName());
        userModel.setStreet(userRegisterRequest.getStreet());
        userModel.setAddress(userRegisterRequest.getAddress());
        userModel.setCity(userRegisterRequest.getCity());
        userModel.setState(userRegisterRequest.getState());
        userModel.setEmail(userRegisterRequest.getEmail());
        userModel.setPhone(userRegisterRequest.getPhone());
        userModel.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        
        Role role = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        userModel.setRoles(Collections.singletonList(role));

        UserModel savedUserModel = userModelRepository.save(userModel);

        return UserRegisterResponse.builder()
                .userId(savedUserModel.getUserId())
                .firstName(savedUserModel.getFirstName())
                .lastName(savedUserModel.getLastName())
                .street(savedUserModel.getStreet())
                .address(savedUserModel.getAddress())
                .city(savedUserModel.getCity())
                .state(savedUserModel.getState())
                .email(savedUserModel.getEmail())
                .phone(savedUserModel.getPhone())
                .build();
    }

    @Override
    public AuthenticationTokenResponse loginUserService(LoginUserRequest loginUserRequest)
            throws BadCredentialsException {

        userModelRepository.findByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException(" Invalid username or password"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.getEmail(),
                        loginUserRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtAuthenticationUtils.generateToken(authentication);
        String message = "Login successfully with email: "+loginUserRequest.getEmail();
        return new AuthenticationTokenResponse(message,token);
    }
    
    
    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {

        Optional<UserModel> userModelOptional = userModelRepository.findByEmail(email);
        
        if (userModelOptional.isPresent()) {
        	
        	UserModel model = userModelOptional.get();
        	
        	UserDto dto = new UserDto();
        	dto.setFirstName(model.getFirstName());
        	dto.setLastName(model.getLastName());
        	dto.setStreet(model.getStreet());
        	dto.setAddress(model.getAddress());
        	dto.setCity(model.getCity());
        	dto.setState(model.getState());
        	dto.setEmail(model.getEmail());
        	dto.setPhone(model.getPhone());
        	dto.setRole(model.getRoles().get(0).getName().toString());
        	
        	return dto;
        }

        throw new UserNotFoundException("User not found");

    }

    @Override
    public Role addRole(Role role) throws RoleNotFoundException {
        if (roleRepository.existsByName(role.getName())) {
            throw new RoleNotFoundException("Role already exists");
        }
        return roleRepository.save(role);
    }
    
    

	private String extractTokenFromResponse(String responseBody) {
		// TODO Auto-generated method stub
		return null;
	}
    
}