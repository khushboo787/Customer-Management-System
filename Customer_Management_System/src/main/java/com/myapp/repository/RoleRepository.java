package com.myapp.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entity.Role;
import com.myapp.entity.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleEnum roleAdmin);
    
    boolean existsByName(RoleEnum roleEnum);

}