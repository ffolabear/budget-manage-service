package com.example.budgetmanageservice.user;

import com.example.budgetmanageservice.common.dto.ResponseDto;
import com.example.budgetmanageservice.user.dto.UserRegisterRequestDto;
import com.example.budgetmanageservice.user.dto.UserRegisterResponseDto;
import com.example.budgetmanageservice.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRegisterRequestDto post) {
        return User.builder()
                .name(post.getUserName())
                .password(post.getPassword())
                .build();

    }

    public ResponseDto<UserRegisterResponseDto> toIdResponseDto(User user) {
        return ResponseDto.<UserRegisterResponseDto>builder()
                .code(HttpStatus.CREATED.value())
                .data(toIdResponse(user))
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

    private UserRegisterResponseDto toIdResponse(User user) {
        return new UserRegisterResponseDto(user.getId());
    }
}
