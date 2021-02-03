package com.dogancanokur.eventManagement.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private ClientRegistration clientRegistration;

	@Autowired
	public UserController(ClientRegistration clientRegistration) {
		this.clientRegistration = clientRegistration;
	}
	@GetMapping("/api/user")
	public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User user) {
		if (user == null) {
			return new ResponseEntity<>("", HttpStatus.OK);
		} else {
			return ResponseEntity.ok().body(user.getAttributes());
		}
	}

	@PostMapping("/api/logout")
	public ResponseEntity<?> logout(HttpServletRequest request,
			@AuthenticationPrincipal(expression = "idToken") OidcIdToken oidcIdToken) {
		String logoutUrl = this.clientRegistration.getProviderDetails().getConfigurationMetadata()
				.get("end_session_endpoint").toString();

		Map<String, String> logoutDetails = new HashMap<>();
		logoutDetails.put("logoutUrl", logoutUrl);
		logoutDetails.put("idToken", oidcIdToken.getTokenValue());
		request.getSession(false).invalidate();
		return ResponseEntity.ok().body(logoutDetails);
	}
}
