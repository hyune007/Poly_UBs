package com.poly.ubs.service;

import com.poly.ubs.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl  extends GenericServiceImpl<User, String, UserRepository>{
    @Autowired
}
