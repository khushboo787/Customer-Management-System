package com.myapp.config;

 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.myapp.entity.Role;
import com.myapp.entity.UserModel;
import com.myapp.repository.UserModelRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationUserDetailsService implements UserDetailsService {


    private final UserModelRepository userModelRepository ;


    public AuthenticationUserDetailsService(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {


             UserModel userModel = userModelRepository
                .findByEmail( username )
                .orElseThrow( () ->
                        new UsernameNotFoundException("User does not exist exception") );
 
        return new User( userModel.getEmail() ,
                userModel.getPassword(),
                mapRoleToUser(userModel.getRoles())
        );
    }

    private Collection<GrantedAuthority> mapRoleToUser(List<Role> roleList){

        return roleList
                .stream()
                .map( role -> new SimpleGrantedAuthority( role.getName().toString() ) )
                .collect(Collectors.toList());

    }

}