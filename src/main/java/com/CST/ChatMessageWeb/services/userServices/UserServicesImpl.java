package com.CST.ChatMessageWeb.services.userServices;

import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
import com.CST.ChatMessageWeb.repository.ContactRepo;
import com.CST.ChatMessageWeb.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {
	private final UserRepo usersRepository;

	private final ContactRepo contactRepository;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	@Override
	public List<UserItemContactDto> takeUserItemContact() {
		return null;
	}

	@Override
	public List<UserItemContactDto> searchAllUserEmail(String searchEmail) {
		if(searchEmail.equals(""))
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
											item, "hello world"
							);
						}
		).toList();
	}
}
