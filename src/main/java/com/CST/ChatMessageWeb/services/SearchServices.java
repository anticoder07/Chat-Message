package com.CST.ChatMessageWeb.services;

import com.CST.ChatMessageWeb.entity.Contact.EStateNotification;
import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
import com.CST.ChatMessageWeb.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServices {
	private final UserRepo usersRepository;

	public List<UserItemContactDto> searchAllUserEmail(String searchEmail) {
		if (searchEmail.equals(""))
			return new ArrayList<>();
		List<Users> userList = usersRepository.searchAllByUserEmail(searchEmail);
		List<Users> filteredList = userList.stream()
						.filter(item -> {
							String[] parts = item.getUserEmail().split("@");
							return parts.length > 0 && parts[0].contains(searchEmail);
						})
						.toList();

		return filteredList.stream().map(
						(item) -> {
							return new UserItemContactDto(
											item, "hello world", EStateNotification.ALL
							);
						}
		).toList();
	}
}
