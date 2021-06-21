package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.IUserMapper;
import com.sakovich.scooterrental.api.service.IUserService;
import com.sakovich.scooterrental.dao.IRoleDao;
import com.sakovich.scooterrental.dao.IUserDao;
import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.User;
import com.sakovich.scooterrental.model.dto.RoleDto;
import com.sakovich.scooterrental.model.dto.UserDto;
import com.sakovich.scooterrental.model.enums.ERole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IRoleDao roleDao;
    private final IUserMapper userMapper;

    private static final Logger log = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(IUserDao userDao, IRoleDao roleDao, IUserMapper userMapper) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(getUserByIdHandler(id));
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public UserDto updateWithRole(UserDto dto) {
        User userToUpdate = userDao.getById(dto.getId());
        userToUpdate.setRole(roleDao.getById(dto.getRoleId()));
        return updateUserData(userToUpdate, dto);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(userDao.getById(id));
    }

    @Override
    public UserDto updateWithoutRole(UserDto dto) {
        User userToUpdate = userDao.getById(dto.getId());
        return updateUserData(userToUpdate, dto);
    }

    private UserDto updateUserData(User userToUpdate, UserDto dto) {
        if (isDtoValid(dto)) {
            userToUpdate.setName(dto.getName());
            userToUpdate.setSurname(dto.getSurname());
            userToUpdate.setEmail(dto.getEmail());
            userToUpdate.setAge(dto.getAge());
            userDao.save(userToUpdate);
        }
        return userMapper.toDto(userDao.getById(dto.getId()));
    }

    private User getUserByIdHandler(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            log.error("Error when trying to get user by id");
            throw new OperationCancelledException("User was not found!");
        }
    }

    private boolean isDtoValid(UserDto dto) {
        if (dto.getEmail() != null && dto.getName() != null && dto.getSurname() != null && dto.getAge() != null) {
            return true;
        } else {
            log.error("Error when trying to add or update user");
            throw new OperationCancelledException("Invalid user parameters!");
        }
    }
}
