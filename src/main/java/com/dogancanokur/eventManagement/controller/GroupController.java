package com.dogancanokur.eventManagement.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dogancanokur.eventManagement.model.Group;
import com.dogancanokur.eventManagement.repository.GroupRepository;

@RestController
@RequestMapping("/api")
public class GroupController {
	private final Logger logger = LoggerFactory.getLogger(GroupController.class);
	private final GroupRepository repository;

	@Autowired
	public GroupController(GroupRepository repository, GroupRepository groupRepository) {
		this.repository = repository;
	}

	@GetMapping("/groups")
	public Collection<Group> groups() {
		return repository.findAll();
	}

	@GetMapping("/group/{id}")
	public ResponseEntity<?> getGroup(@PathVariable Long id) {
		Optional<Group> group = repository.findById(id);
		return group.map(r -> ResponseEntity.ok().body(r)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/group")
	public ResponseEntity<Group> craeteGroup(@RequestBody Group group) throws URISyntaxException {
		logger.info("Request to create group: {}", group);
		Group result = repository.save(group);
		return ResponseEntity.created(new URI("/api/group/" + result.getId())).body(result);
	}

	@PutMapping("/group/{id}")
	public ResponseEntity<Group> updateGroup(@RequestBody Group groupRequest) {
		logger.info("Request to update group: {}", groupRequest);
		Group group = repository.save(groupRequest);
		return ResponseEntity.ok().body(group);
	}

	@DeleteMapping("/group/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		logger.info("Request to delete group:{}", id);
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
