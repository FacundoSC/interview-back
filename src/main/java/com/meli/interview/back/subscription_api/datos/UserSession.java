package com.meli.interview.back.subscription_api.datos;

import com.meli.interview.back.subscription_api.datos.DTO.UserRequestDTO;
import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;
import com.meli.interview.back.subscription_api.service.UserService;
import com.meli.interview.back.subscription_api.service.impl.UserServiceImpl;
import com.meli.interview.back.subscription_api.util.JWTUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {

    private static final UserSession userSession = new UserSession();

    private UserService userService;

    private JWTUtil jwtUtil = new JWTUtil();

    private String jwt;

    private UserSession() {
    }

    public static UserSession getInstance() {
        return userSession;
    }

    public User getLoggedUser() throws UserNotLoggedInException {
        String username = jwtUtil.getValue(this.jwt);

        if (!username.isEmpty()) {
            return userService.getUserByUsername(username);
        }
        throw new UserNotLoggedInException("No hay ningún usuario logueado actualmente");
    }

    public String obtenerToken(UserServiceImpl userService, UserRequestDTO user) throws Exception {
        setUserService(userService);
        User usuarioLogueado = userService.obtenerUsuarioPorCredenciales(user);
        if (usuarioLogueado != null) {
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getUsername()), user.getUsername());
            this.jwt = tokenJwt;
            return tokenJwt;
        }
        throw new UserNotLoggedInException("Usuario no registrado");
    }
}