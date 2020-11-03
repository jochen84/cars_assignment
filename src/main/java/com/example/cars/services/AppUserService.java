package com.example.cars.services;

import com.example.cars.entities.AppUser;
import com.example.cars.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Cacheable(value = "appUsersCache")
    public List<AppUser> findAll(String name, String email, boolean sortByName) {
        log.info("Request to find all users");
        var users = appUserRepository.findAll();
        if (name != null) {
            users.stream()
                    .filter(appUser -> appUser.getUsername().toLowerCase().contains(name) || appUser.getFirstname().toLowerCase().contains(name) || appUser.getLastname().toLowerCase().contains(name))
                    .collect(Collectors.toList());
        }
        if (email != null) {
            users.stream()
                    .filter(appUser -> appUser.getEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
        }
        if (sortByName) {
            users.sort(Comparator.comparing(AppUser::getLastname));
        }
        return users;
    }

    @Cacheable(value = "appUsersCache", key = "#id")
    public AppUser findById(String id) {
        var isSuperman = checkAuthority("ADMIN") || checkAuthority("CARDEALER");
        var isCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals(appUserRepository.findById(id).get().getUsername());
        if (!isSuperman && !isCurrentUser) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized! You can only view your own details");
        }
        return appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with that id"));
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with that username"));
    }

    @CachePut(value = "appUsersCache", key = "#result.id")
    public AppUser save(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @CachePut(value = "appUsersCache", key = "#id")
    public void update(String id, AppUser appUser) {
        if (!appUserRepository.existsById(id)) {
            log.error("Could not find a user with that id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with that id");
        }
        var isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().toUpperCase().equals("ROLE_ADMIN"));
        var isCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals(appUser.getUsername().toLowerCase());
        if (!isAdmin && !isCurrentUser) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized! You can only update your own details");
        }
        if (appUser.getPassword().length() <= 16) {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        }
        appUser.setId(id);
        appUserRepository.save(appUser);
    }

    @CacheEvict(value = "appUserCache", key = "#id")
    public void delete(String id) {
        if (!appUserRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with that id");
        }
        var isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().toUpperCase().equals("ROLE_ADMIN"));
        var isCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals(appUserRepository.findById(id).get().getUsername());
        if (!isAdmin && !isCurrentUser) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized! You can only delete your own details");
        }
        appUserRepository.deleteById(id);
    }

    private boolean checkAuthority(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().toUpperCase().equals("ROLE_" + role));
    }
}