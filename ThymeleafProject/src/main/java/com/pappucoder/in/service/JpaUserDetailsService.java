package com.pappucoder.in.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pappucoder.in.model.UserEntity;
import com.pappucoder.in.repository.UserRepository;


@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
//        UserEntity user = userOpt.get();
//        Collection<GrantedAuthority> authorities = Arrays.stream(user.getRole().split(","))
//                .map(String::trim)
//                .map(role -> new SimpleGrantedAuthority(role))
//                .collect(Collectors.toList());
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                authorities
//        );
        
        return  org.springframework.security.core.userdetails.User.builder()
				.username(userOpt.get().getUsername())
				.password(userOpt.get().getPassword())
				.authorities(Arrays.stream(userOpt.get().getRole().split(","))
						.map(String::trim)
						.map(role -> new SimpleGrantedAuthority(role))
						.collect(Collectors.toList()))
				.build();
        
    }
	
}