package com.myapp.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entity.UserModel;

import java.util.Optional;

public interface UserModelRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserModel> findByFirstNameAndLastName(String firstName , String lastName);

}