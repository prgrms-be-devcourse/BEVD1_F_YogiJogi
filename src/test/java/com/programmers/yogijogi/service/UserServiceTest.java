package com.programmers.yogijogi.service;

import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.UserCreateRequestDto;
import com.programmers.yogijogi.entity.dto.UserCreateResponseDto;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import com.programmers.yogijogi.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Service
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .name("KIM")
                .build();
        //When
        UserCreateResponseDto dto = userService.join(userCreateRequestDto);
        User findUser = userRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        //Then

        Assertions.assertThat(dto.getId()).isEqualTo(findUser.getId());

        // assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .name("KIM")
                .build();

        UserCreateRequestDto userCreateRequestDto2 = UserCreateRequestDto.builder()
                .name("KIM")
                .build();

        //When

        userService.join(userCreateRequestDto);
        // userService.join(user2);

        //Then

        assertThrows(IllegalStateException.class, () -> userService.join(userCreateRequestDto2));
        //assertThrows(com.programmers.yogijogi.exception.NotFoundException.class, () -> roomService.findOneByDate(savedRoomId1, checkIn, checkOut));

    }
}