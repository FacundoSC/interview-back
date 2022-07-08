package com.meli.interview.back.subscription_api.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.interview.back.subscription_api.datos.User;
import com.meli.interview.back.subscription_api.service.UserService;
import com.meli.interview.back.subscription_api.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(UserController.class)
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    /*
    @Autowired
    private User user;
    */

    @MockBean
    private UserService userService;

    @MockBean
    private JWTUtil jwtUtil;

    ObjectMapper objMa = new ObjectMapper();

    @Test
    void userFindAllTest() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(new User()));
        mvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveUsuarioTest() throws Exception {
      User usuario = new User("name","username", "3214");
      when(userService.save(any())).thenReturn(usuario);
      mvc.perform(post("/api/v1/user/save").contentType(MediaType.APPLICATION_JSON)
              .content(objMa.writeValueAsString(usuario)))
              .andExpect(jsonPath("$.id").value(1))
              .andExpect(jsonPath("$.name").value("name"));

       }




}
