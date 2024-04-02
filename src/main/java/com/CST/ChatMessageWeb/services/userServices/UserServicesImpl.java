package com.CST.ChatMessageWeb.services.userServices;

import com.CST.ChatMessageWeb.entity.Contacts;
import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
import com.CST.ChatMessageWeb.repository.ContactRepo;
import com.CST.ChatMessageWeb.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices{
	private UserRepo usersRepository;

	private ContactRepo contactRepository;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	@Override
	public List<UserItemContactDto> takeUserItemContact() {
//		List<Contacts> contactsList = contactRepository.findAllByUserSend(getUser());
//		List<>
		return null;
	}
}
