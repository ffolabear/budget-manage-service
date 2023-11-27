package com.example.budgetmanageservice.security.service;

import com.example.budgetmanageservice.security.dto.APIUserDto;
import com.example.budgetmanageservice.user.entity.APIUser;
import com.example.budgetmanageservice.user.repository.APIUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class APIUserDetailService implements UserDetailsService {

    private final APIUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<APIUser> result = apiUserRepository.findById(username);
        APIUser apiUser = result.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        log.info("APIUserDetailService apiUser ================================");

        APIUserDto dto = new APIUserDto(
                apiUser.getMid(),
                apiUser.getMpw(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        log.info(String.valueOf(dto));
        return dto;
    }
}
