package com.example.budgetmanageservice.security.dto;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@ToString
@Getter
@Setter
public class APIUserDto extends User {

    private final String mid;
    private final String mpw;

    public APIUserDto(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mid = username;
        this.mpw = password;
    }
}
