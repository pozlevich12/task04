package com.table.controllers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import com.table.entity.AuthProvider;
import com.table.entity.User;
import com.table.repository.UserRepository;

@RestController
public class MainRestController {

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/chart")
    public Map<String, Object> chart(){
        Map<String, Object> providers = new HashMap<String, Object>();
        providers.put("google", userRepository.findByAuthProvider(AuthProvider.GOOGLE).size());
        providers.put("github", userRepository.findByAuthProvider(AuthProvider.GITHUB).size());
        providers.put("azure", userRepository.findByAuthProvider(AuthProvider.AZURE).size());
        providers.put("count", userRepository.count());
        return providers;
    }

 
    
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {

        AuthProvider authProvider;
        String providerKey;
    
        if (principal.getAttribute("login") != null) {
            authProvider = AuthProvider.GITHUB;
            providerKey = principal.getAttribute("id").toString();
        } else if (principal.getAttribute("aio") != null) {
            authProvider = AuthProvider.AZURE;
            providerKey = principal.getAttribute("sub");
        } else {
            authProvider = AuthProvider.GOOGLE;
            providerKey = principal.getAttribute("sub");
        }

        List<User> selectedUser = userRepository.findByProviderKeyAndAuthProvider(providerKey, authProvider);
        if (selectedUser.isEmpty()) {
            User user = new User();
            user.setProviderKey(providerKey);
            user.setProvider(authProvider);
            user.setFirstVisit(LocalDateTime.now());
            user.setLastVisit(LocalDateTime.now());
            user.setName(principal.getAttribute("name"));
            user.setBlocked(false);
            userRepository.save(user);

        } else {
            selectedUser.get(0).setLastVisit(LocalDateTime.now());
            userRepository.save(selectedUser.get(0));
        }
        
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
}