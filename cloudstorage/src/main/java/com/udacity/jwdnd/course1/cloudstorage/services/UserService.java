package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    @Autowired UserMapper userMapper;

    @Autowired HashService hashService;

    public int createUser(Users user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashValue = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insertUser(new Users(null, user.getUserName(), encodedSalt, hashValue, user.getFirstName(), user.getLastName()));
    }

    public Integer getUserId(String username) {
        return userMapper.findByUserName(username).getUserId();
    }
}
