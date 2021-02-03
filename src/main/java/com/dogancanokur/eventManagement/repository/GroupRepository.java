package com.dogancanokur.eventManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dogancanokur.eventManagement.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	Group findByName(String name);
	List<Group> findAllByUserId(String id);
}
