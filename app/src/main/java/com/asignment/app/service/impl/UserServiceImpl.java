package com.asignment.app.service.impl;

import com.asignment.app.entity.User;
import com.asignment.app.repository.UserRepository;
import com.asignment.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean validateUser(String token_user_name,Long uId) {
       User user =  userRepository.findByuName(token_user_name);
        return user.getUid().equals(uId);
    }

    @Override
    public User findByUserName(String uName) {
      return userRepository.findByuName(uName);
    }
}
