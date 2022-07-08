package com.meli.interview.back.subscription_api.service.impl;

import com.meli.interview.back.subscription_api.datos.Subscription;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.datos.UserSession;
import com.meli.interview.back.subscription_api.datos.DTO.UsernameDto;
import com.meli.interview.back.subscription_api.exception.FriendNotFoundException;
import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;
import com.meli.interview.back.subscription_api.repository.SubscriptionRepository;
import com.meli.interview.back.subscription_api.repository.UserRepository;
import com.meli.interview.back.subscription_api.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository ;

    /**
     * Devuelve el costo total de las suscripciones de un usuario siempre que el usuario que esté logueado,
     * se encuentre en su lista de amigos
     *
     * @param userdto
     * @return costo total de la suscripciones del user
     * @throws UserNotLoggedInException si no hay un usuario logueado
     */

    @Override
    public Float getUserSubscriptionsCost(UsernameDto userdto) throws Exception {
        User friend = userRepository.findByUsername(userdto.getUsername());

        if (!Objects.isNull(friend)) {
            ArrayList<Subscription> subscriptionList;

            User loggedUser = UserSession.getInstance().getLoggedUser();

            if (loggedUser != null) {
                boolean isFriend = friend.getFriends().contains(loggedUser);
                //realizar con stream parallel.

                if (isFriend) {
                    subscriptionList = new ArrayList<Subscription>(friend.getSubscriptions());

                    return (float) subscriptionList.stream()
                            .mapToDouble(Subscription::getPrice)
                            .sum();
                }
                throw new FriendNotFoundException("El usuario indicado no figura en la lista de amigos.");
            } else {
                throw new UserNotLoggedInException("No hay ningún usuario logeado actualmente");
            }
        } else {
            throw new EntityNotFoundException("El usuario indicado no existe");
        }
    }

}