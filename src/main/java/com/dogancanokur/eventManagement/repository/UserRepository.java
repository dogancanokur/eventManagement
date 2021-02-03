package com.dogancanokur.eventManagement.repository;

import com.dogancanokur.eventManagement.model.Group;
import com.dogancanokur.eventManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
}
