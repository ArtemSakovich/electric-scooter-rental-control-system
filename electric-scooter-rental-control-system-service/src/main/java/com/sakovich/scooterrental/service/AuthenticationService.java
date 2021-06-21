package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.dao.IRoleDao;
import com.sakovich.scooterrental.dao.IUserDao;
import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.User;
import com.sakovich.scooterrental.model.dto.request.SignupRequest;
import com.sakovich.scooterrental.model.dto.response.MessageResponse;
import com.sakovich.scooterrental.model.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {

    private final IUserDao userDao;
    private final IRoleDao roleDao;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthenticationService(IUserDao userDao, IRoleDao roleDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userDao.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        Role userRole = roleDao.getRoleByName(ERole.ROLE_USER.toString());

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
