package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.entity.dto.*;
import com.programmers.yogijogi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserApiControllerTest {
    @Autowired
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void user_Save() throws Exception {

        UserCreateRequestDto dto = UserCreateRequestDto.builder()
                .name("chae")
                .build();


        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void user_Update() throws Exception {

        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .name("KIM")
                .build();

        UserCreateResponseDto saveUser = userService.join(userCreateRequestDto);
        Long userId = saveUser.getId();
        UserUpdateRequestDto dto = UserUpdateRequestDto.builder()
                .name("chae")
                .build();


        mockMvc.perform(put("/users/{id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void findUser() throws Exception {

        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .name("KIM")
                .build();

        UserCreateResponseDto saveUser = userService.join(userCreateRequestDto);
        Long userId = saveUser.getId();

        mockMvc.perform(get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}