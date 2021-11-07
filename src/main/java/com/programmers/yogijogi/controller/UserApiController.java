package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.*;
import com.programmers.yogijogi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserApiController {

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    //회원가입
    @PostMapping("/members")
    public ResponseEntity<UserCreateResponseDto> saveUser(@RequestBody @Valid
                                                     UserCreateRequestDto userCreateRequestDto) {

    UserCreateResponseDto dto =  userService.join(userCreateRequestDto);

        return ResponseEntity.ok(dto);
    }

    //회원정보 수정
    @PutMapping("/members/{id}")
    public ResponseEntity<UserUpdateResponseDto>  updateUser(@PathVariable("id") Long id,
                                                                 @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        UserUpdateResponseDto dto = userService.update(id, userUpdateRequestDto.getName());
        return ResponseEntity.ok(dto);
    }

    //회원정보 조회
    @GetMapping("/members/{id}")
    public ResponseEntity<UserDetailDto> findUser(@PathVariable("id") Long id) {
        UserDetailDto userDetailDto = userService.findOne(id);
        return ResponseEntity.ok(userDetailDto);
    }
}
