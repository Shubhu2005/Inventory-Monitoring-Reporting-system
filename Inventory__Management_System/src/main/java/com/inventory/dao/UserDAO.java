package com.inventory.dao;

import com.inventory.model.User;

public interface UserDAO {
    boolean signup(User user);
    User login(String username, String password);

    User getUserByUsername(String username);
}
