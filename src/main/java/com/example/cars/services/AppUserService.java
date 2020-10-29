package com.example.cars.services;

import com.example.cars.entities.AppUser;
import com.example.cars.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public List<AppUser>
}
