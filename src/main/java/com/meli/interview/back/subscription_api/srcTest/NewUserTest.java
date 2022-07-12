package com.meli.interview.back.subscription_api.srcTest;

import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.datos.DTO.UsernameDto;
import com.meli.interview.back.subscription_api.datos.Subscription;
import com.meli.interview.back.subscription_api.datos.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewUserTest {


    public User newUserSesion() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("fcordoba");
        userRequestDTO.setPassword("prueba123");
        User currentUser = new User();
        currentUser.setName("Facundo");
        currentUser.setUsername("fcordoba");
        currentUser.setPassword("prueba123");
        currentUser.setId(1);
        User userUno = new User("Micky", "mscurrell0@unicef.org", "a5fjETDIbpl");
        userUno.setId(2);
        List<User> userList = new ArrayList<>();
        userList.add(userUno);
        currentUser.setFriends(userList);
        return currentUser;

    }

    public User newUserFriendMicky() {
        User newFriend = new User("Micky", "mscurrell0@unicef.org", "a5fjETDIbpl");
        newFriend.setId(2);
        List<User> listfriend = new ArrayList<>();
        newFriend.setFriends(listfriend);
        List<Subscription> susbscriptionList = new ArrayList<>();
        newFriend.setSubscriptions(susbscriptionList);
        return newFriend;
    }


    public UsernameDto newUseNameDTOFriendMicky() {
        UsernameDto usernameDto = new UsernameDto();
        usernameDto.setUsername("mscurrell0@unicef.org");

        return usernameDto;


    }

    public UserRequestDTO newUserRequestDTOMicky() {

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("mscurrell0@unicef.org");
        userRequestDTO.setPassword("a5fjETDIbpl");

        return userRequestDTO;
    }
    public List<User> newUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(newUserFriendMicky());
        userList.add(newUserFriendKonstantine());

        return userList;
    }

    public User newUserFriendKonstantine() {
        User newFriend = new User("Konstantine", "kconnettrr@discovery.com", "a5fjETDIbpl");
        List<User> listfriend = new ArrayList<>();
        newFriend.setFriends(listfriend);
        return newFriend;
    }


    public UsernameDto newUseNameDTOFriendKonstantine() {
        UsernameDto usernameDto = new UsernameDto();
        usernameDto.setUsername("kconnettrr@discovery.com");

        return usernameDto;

    }

    public UserRequestDTO newUserRequestDTOSesion() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("fcordoba");
        userRequestDTO.setPassword("prueba123");

        return userRequestDTO;

    }
}
