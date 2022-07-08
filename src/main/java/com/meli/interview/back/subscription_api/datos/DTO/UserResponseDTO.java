package com.meli.interview.back.subscription_api.datos.DTO;

import com.meli.interview.back.subscription_api.datos.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserResponseDTO {

    private String name;
    private String username;
    private List<String> friendsUsername;
    private List<Subscription> subscriptions;

    public UserResponseDTO(String name, String username) {
        this.name = name;
        this.username = username;
    }
}
