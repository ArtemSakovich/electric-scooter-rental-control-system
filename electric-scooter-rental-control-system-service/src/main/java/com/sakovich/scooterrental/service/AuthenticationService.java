package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.User;
import com.sakovich.scooterrental.model.dto.request.SignupRequest;
import com.sakovich.scooterrental.model.dto.response.MessageResponse;
import com.sakovich.scooterrental.model.enums.ERole;
import com.sakovich.scooterrental.repository.IRoleRepository;
import com.sakovich.scooterrental.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userDao;
    private final IRoleRepository roleDao;
    private final PasswordEncoder encoder;

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userDao.existsByEmail(signUpRequest.getEmail())) {
            log.error("Error when trying to register user. Email is already taken!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        Role userRole = roleDao.getByName(ERole.ROLE_USER.toString());

        User user = new User(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getAge(),
                userRole);

        userDao.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
