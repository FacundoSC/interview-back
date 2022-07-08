package com.meli.interview.back.subscription_api.service;

import com.meli.interview.back.subscription_api.datos.User;

import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.exception.UserNotFoundException;
import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;


import java.util.List;

public interface UserService {
    User getUser(Integer id);

    User save(User user);

    List<User> findAll();

    User obtenerUsuarioPorCredenciales(UserRequestDTO usuario) throws UserNotLoggedInException;

    User getUserByUsername(String username) throws UserNotFoundException;

    User addFriend(String newFriendUsername);
}
