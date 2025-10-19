package org.elis.horizon.horizonrent.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        if (!users.containsKey(email)) {
            User user = new User();
            user.setEmail(email);
            user.setName(oauth2User.getAttribute("name"));
            user.setGivenName(oauth2User.getAttribute("given_name"));
            user.setFamilyName(oauth2User.getAttribute("family_name"));
            user.setPicture(oauth2User.getAttribute("picture"));
            users.put(email, user);
        }

        System.out.println("OAuth2 User Attributes: " + oauth2User.getAttributes());
        System.out.println("Stored users: " + users);

        return oauth2User;
    }

    public static class User {
        private String email;
        private String name;
        private String givenName;
        private String familyName;
        private String picture;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        @Override
        public String toString() {
            return "User{" +
                    "email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", givenName='" + givenName + '\'' +
                    ", familyName='" + familyName + '\'' +
                    ", picture='" + picture + '\'' +
                    '}';
        }
    }
}