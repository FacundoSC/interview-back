package com.meli.interview.back.subscription_api.service;

import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UserRequestDTO;
import com.meli.interview.back.subscription_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }



    @Override
    public User obtenerUsuarioPorCredenciales(UserRequestDTO usuario) throws Exception {
     User user = userRepository.findByUsername(usuario.getUsername());
     if(user != null || (user.getPassword().equals(usuario.getPassword()))){
         return user;
     }
     throw new Exception("las contraseñas no coinciden");
    }


}
