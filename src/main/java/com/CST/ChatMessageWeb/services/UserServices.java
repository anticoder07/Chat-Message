package com.CST.ChatMessageWeb.services;

import com.CST.ChatMessageWeb.entity.Contact.EStateNotification;
import com.CST.ChatMessageWeb.entity.Contact.UsersContacts;
import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
import com.CST.ChatMessageWeb.repository.UserRepo;
import com.CST.ChatMessageWeb.repository.UsersContactRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServices {
	private final UserRepo userRepository;

	private final UsersContactRepo usersContactRepository;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	public UserItemContactDto getUserById(Long id) {
		Users users = userRepository.findById(id).orElse(null);
		if (users == null) return null;
		return new UserItemContactDto(users, "hello world", EStateNotification.ALL);
	}

	public List<UserItemContactDto> getAllUserContact() {
		List<UsersContacts> usersContactsList1 = usersContactRepository.findByUser1(getUser());
		List<UsersContacts> usersContactsList2 = usersContactRepository.findByUser2(getUser());
		usersContactsList1.addAll(usersContactsList2);

		usersContactsList1.sort(Comparator.comparing(UsersContacts::getLastContactTime).reversed());

		List<UserItemContactDto> userItemContactDtoList = new ArrayList<>();

		usersContactsList1.forEach(item -> {
			Users users = item.getUser1().getId().equals(getUser().getId()) ? item.getUser2() : item.getUser1();
			if (users != null) {
				userItemContactDtoList.add(new UserItemContactDto(users, "hello world", item.getStateNotification()));
			}
		});


		return userItemContactDtoList;
	}
}
