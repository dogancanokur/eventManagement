package com.dogancanokur.eventManagement.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.dogancanokur.eventManagement.model.Group;
import com.dogancanokur.eventManagement.model.User;
import com.dogancanokur.eventManagement.repository.GroupRepository;
import com.dogancanokur.eventManagement.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class GroupController {
	private final Logger logger = LoggerFactory.getLogger(GroupController.class);
	private final GroupRepository groupRepository;
	private final UserRepository userRepository;

	@Autowired
	public GroupController(GroupRepository groupRepository, UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/groups")
	public Collection<Group> groups(Principal principal) {
		return groupRepository.findAllByUserId(principal.getName());
	}

	@GetMapping("/group/{id}")
	public ResponseEntity<?> getGroup(@PathVariable Long id) {
		Optional<Group> group = groupRepository.findById(id);
		return group.map(r -> ResponseEntity.ok().body(r)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/group")
	public ResponseEntity<Group> craeteGroup(@RequestBody Group group, @AuthenticationPrincipal OAuth2User principal)
			throws URISyntaxException {
		logger.info("Request to create group: {}", group);
		Map<String, Object> details = principal.getAttributes();
		String userId = details.get("sub").toString();
		// check if user already exist
		Optional<User> user = userRepository.findById(userId);
		group.setUser(user.orElse(new User(userId, details.get("name").toString(), details.get("email").toString())));

		Group result = groupRepository.save(group);
		return ResponseEntity.created(new URI("/api/group/" + result.getId())).body(result);
	}

	@PutMapping("/group/{id}")
	public ResponseEntity<Group> updateGroup(@RequestBody Group groupRequest) {
		logger.info("Request to update group: {}", groupRequest);
		Group group = groupRepository.save(groupRequest);
		return ResponseEntity.ok().body(group);
	}

	@DeleteMapping("/group/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		logger.info("Request to delete group:{}", id);
		groupRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
