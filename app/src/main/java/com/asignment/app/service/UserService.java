package com.asignment.app.service;

import com.asignment.app.entity.User;

public interface UserService {
    boolean validateUser(String uName,Long uId);
    User findByUserName(String uName);
}
