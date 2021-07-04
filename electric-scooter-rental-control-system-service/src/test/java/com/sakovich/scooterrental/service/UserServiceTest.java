package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.IUserMapper;
import com.sakovich.scooterrental.repository.IUserRepository;
import com.sakovich.scooterrental.model.User;
import com.sakovich.scooterrental.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IUserRepository userDao;

    @InjectMocks
    UserService userService;

    @Mock
    IUserMapper userMapper;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).email("Some email...").age(19).build();
    }

    @Test
    void getAll() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).email("Some email_1...").age(19).build());
        users.add(User.builder().id(2L).email("Some email_2...").age(20).build());
        users.add(User.builder().id(3L).email("Some email_3...").age(21).build());

        given(userDao.findAll()).willReturn(users);

        int receivedSize = userService.getAll().size();

        assertEquals(users.size(), receivedSize);
    }

    @Test
    void findByIdFound() {
        given(userDao.findById(1L)).willReturn(java.util.Optional.of(new User()));
        given(userMapper.toDto(any(User.class))).willReturn(new UserDto());
        UserDto returnedUser = (userService.getById(1L));
        assertNotNull(returnedUser);
    }

    @Test
    void delete() {
        given(userDao.getById(1L)).willReturn(new User());
        userService.delete(1L);
        then(userDao).should(times(1)).delete(any(User.class));
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void getUserByEmail() {
        given(userDao.getByEmail("email")).willReturn(new User());
        User returnedUser = (userService.getUserByEmail("email"));
        assertNotNull(returnedUser);
    }
}