package com.api.product.auth.services;

import com.api.product.auth.dto.UserInfoUserDetails;
import com.api.product.entity.UserInfo;
import com.api.product.repository.UserInfoRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserInfoRepo userInfoRepository;

    public UserInfoUserDetailsService(UserInfoRepo userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = Optional.ofNullable(userInfoRepository.findByUsername(identifier))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userInfo.map(UserInfoUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}