package com.meli.interview.back.subscription_api.repository;

import com.meli.interview.back.subscription_api.datos.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserRepositoryTestDos {

    @Autowired
    private UserRepository userRepository;



    @Test
    //Es necesario probar que pasa si envio nulo o no existe username
    void findByUsername() {
        User user = userRepository.findByUsername("mscurrell0@unicef.org");
        assertNotNull(user);
        assertEquals(2, user.getId());
        assertEquals("Micky", user.getName());
    }

    @Test
    void findById() {
        Optional<User> user = userRepository.findById(1);
        assertNotNull(user);
        assertEquals(1, user.orElseThrow().getId());
        assertEquals("fcordoba", user.orElseThrow().getUsername());
    }

    @Test
    void findAll() {
        List<User> user = userRepository.findAll();
        assertFalse(user.isEmpty());
        assertTrue(user.size()>1);
    }

    @Test
    void save() {
        User user =  new User("PruebaNombre", "PruebaUsername", "PruebaContraseña");
        User userSave= userRepository.save(user);
        assertNotNull(userSave);
        assertNotNull(userSave.getId());
        assertTrue(userSave.getId()>0);
        assertEquals("PruebaNombre", userSave.getName());
    }

   /* @Test
    void getById() {
        User user =userRepository.getById(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("fcordoba", user.getUsername());
    }*/
}