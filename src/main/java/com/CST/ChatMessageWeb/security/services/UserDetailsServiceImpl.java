package com.CST.ChatMessageWeb.security.services;

import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String userGmail) throws UsernameNotFoundException {
		Users users = userRepo.findByUserEmail(userGmail)
						.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userGmail));
		UserDetails userDetails = new UserDetailsImpl(users);
		return userDetails;
	}
}
