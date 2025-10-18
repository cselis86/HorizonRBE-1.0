package org.elis.horizon.horizonrent.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Here you can process the user information from Google
        // For example, you can check if the user already exists in your database
        // and create a new user or update an existing one.
        // You can also extract specific attributes from oauth2User and store them
        // in a custom UserDetails object if needed.

        System.out.println("OAuth2 User Attributes: " + oauth2User.getAttributes());

        return oauth2User;
    }
}
