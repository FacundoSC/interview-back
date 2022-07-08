package com.meli.interview.back.subscription_api.service.impl;

import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UserResponseDTO;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UserSession;
import com.meli.interview.back.subscription_api.exception.UserNotFoundException;
import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;
import com.meli.interview.back.subscription_api.repository.UserRepository;
import com.meli.interview.back.subscription_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO save(User user) {
        User newUser = userRepository.save(user);
        return new UserResponseDTO(newUser.getName(), newUser.getUsername());
    }

    @Override
    public List<UserResponseDTO> findAll() {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        for (User user : userList) { //TODO Se podría aplicar parallel para que sea más rápido
            userResponseDTOList.add(new UserResponseDTO(user.getName(),
                    user.getUsername(),
                    convertFriendList(user.getFriends()),
                    user.getSubscriptions()));
        }

        return userResponseDTOList;
    }

    @Override
    public User obtenerUsuarioPorCredenciales(UserRequestDTO usuario) throws UserNotLoggedInException {
        User user = userRepository.findByUsername(usuario.getUsername());

        if (user != null && (user.getPassword().equals(usuario.getPassword()))) {
            return user;
        } else {
            throw new UserNotLoggedInException("Usuario o contraseña inválidos");
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("El usuario perteneciente al token no existe");
        }
    }

    public UserResponseDTO addFriend(String newFriendUsername) {

        User newFriend = checkUserExistence(newFriendUsername);
        User currentUser = UserSession.getInstance().getLoggedUser();

        if (!currentUser.getFriends().contains(newFriend)) {
            currentUser.addFriend(newFriend);
            newFriend.addFriend(currentUser);

            userRepository.save(newFriend);
            userRepository.save(currentUser);
        }

        return new UserResponseDTO(currentUser.getName(),
                currentUser.getUsername(),
                convertFriendList(currentUser.getFriends()),
                currentUser.getSubscriptions());
    }

    private User checkUserExistence(String usernameToCheck) {
        User userToCheck = userRepository.findByUsername(usernameToCheck);

        if (userToCheck != null) {
            return userToCheck;
        } else {
            throw new UserNotFoundException("El usuario no existe");
        }
    }

    private ArrayList<String> convertFriendList(List<User> friendList) {
        ArrayList<String> friendStringList = new ArrayList<>();

        if (!friendList.isEmpty()) {
            for (User user : friendList) { //TODO Se podría aplicar parallel para que sea más rápido
                friendStringList.add(user.getUsername());
            }
        }

        return friendStringList;
    }

}
