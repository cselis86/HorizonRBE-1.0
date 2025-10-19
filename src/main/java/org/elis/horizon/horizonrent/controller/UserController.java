package org.elis.horizon.horizonrent.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/api/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal Object principal) {
        if (principal instanceof OAuth2User) {
            return ((OAuth2User) principal).getAttributes();
        } else if (principal instanceof UserDetails) {
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("name", ((UserDetails) principal).getUsername());
            userDetails.put("email", ((UserDetails) principal).getUsername()); // Assuming username is email for simplicity
            // Add other relevant UserDetails properties if needed
            return userDetails;
        }
        return Collections.emptyMap();
    }
}
