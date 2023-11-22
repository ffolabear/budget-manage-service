package com.example.budgetmanageservice.user.service;

import com.example.budgetmanageservice.common.dto.ResponseDto;
import com.example.budgetmanageservice.common.exception.CommonException;
import com.example.budgetmanageservice.user.UserMapper;
import com.example.budgetmanageservice.user.dto.UserRegisterRequestDto;
import com.example.budgetmanageservice.user.dto.UserRegisterResponseDto;
import com.example.budgetmanageservice.user.entity.User;
import com.example.budgetmanageservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public ResponseDto<UserRegisterResponseDto> saveUser(UserRegisterRequestDto postDto) {
        validUsernameExist(postDto);
        postDto.setPassword(encoder.encode(postDto.getPassword()));

        User requestEntity = mapper.toEntity(postDto);

        User save = repository.save(requestEntity);
        return mapper.toIdResponseDto(save);
    }

    private void validUsernameExist(UserRegisterRequestDto post) {
        repository.findUserByName(post.getUserName()).ifPresent(data -> {
            throw new CommonException(HttpStatus.CONFLICT, "사용할 수 없는 username 입니다.");
        });
    }
}
