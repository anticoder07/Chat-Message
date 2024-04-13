package com.CST.ChatMessageWeb.services;

import com.CST.ChatMessageWeb.entity.Contact.Contacts;
import com.CST.ChatMessageWeb.entity.Contact.EStateNotification;
import com.CST.ChatMessageWeb.entity.Contact.UsersContacts;
import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.payload.dto.ChatMessage;
import com.CST.ChatMessageWeb.payload.dto.ContactDto;
import com.CST.ChatMessageWeb.repository.ContactRepo;
import com.CST.ChatMessageWeb.repository.UserRepo;
import com.CST.ChatMessageWeb.repository.UsersContactRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServices {
	private final UserRepo userRepository;

	private final UsersContactRepo usersContactRepository;

	private final ContactRepo contactRepository;

	private Users getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUserEmail(userDetails.getUsername()).orElseThrow();
	}

	private List<Long> detachedRoomId(String roomId) {
		String[] parts = roomId.split("_");
		Long senderId = Math.min(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
		Long receiverId = Math.max(Long.parseLong(parts[0]), Long.parseLong(parts[1]));

		List<Long> ids = new ArrayList<>();
		ids.add(senderId);
		ids.add(receiverId);

		return ids;
	}

	private boolean reviewUser1_User2(List<Long> roomId, String senderId) {
		return roomId.get(0).equals(Long.parseLong(senderId));
	}

	public boolean saveMessage(ChatMessage chatMessage, String roomId) {
		Long senderId = detachedRoomId(roomId).get(0);
		Long receiverId = detachedRoomId(roomId).get(1);

		Users user1 = userRepository.findById(senderId).orElse(null);
		if (user1 == null) return false;

		Users user2 = userRepository.findById(receiverId).orElse(null);
		if (user2 == null) return false;

		EStateNotification stateNotification = reviewUser1_User2(detachedRoomId(roomId), chatMessage.getSenderId()) ? EStateNotification.USER2 : EStateNotification.USER1;

		UsersContacts usersContacts = usersContactRepository.findByUser1AndUser2(user1, user2).orElse(null);
		if (usersContacts == null) {
			UsersContacts newUsersContacts = new UsersContacts(
							user1,
							user2,
							stateNotification
			);
			usersContactRepository.save(newUsersContacts);

			Date currentChat = new Date();
			Contacts newContacts = new Contacts(
							chatMessage.getContent(),
							currentChat,
							newUsersContacts,
							Long.parseLong(chatMessage.getSenderId())
			);
			contactRepository.save(newContacts);

			newUsersContacts.setLastContactTime(currentChat);
			usersContactRepository.save(newUsersContacts);
		} else {
			Date currentChat = new Date();
			Contacts newContacts = new Contacts(
							chatMessage.getContent(),
							currentChat,
							usersContacts,
							Long.parseLong(chatMessage.getSenderId())
			);
			contactRepository.save(newContacts);

			usersContacts.setLastContactTime(currentChat);
			usersContacts.setStateNotification(stateNotification);
			usersContactRepository.save(usersContacts);
		}

		return true;
	}

	public List<ContactDto> getMessageById(String roomId) {
		List<ContactDto> contactDtoList = new ArrayList<>();
		Long senderId = detachedRoomId(roomId).get(0);
		Long receiverId = detachedRoomId(roomId).get(1);

		Users user1 = userRepository.findById(senderId).orElse(null);
		if (user1 == null) return contactDtoList;

		Users user2 = userRepository.findById(receiverId).orElse(null);
		if (user2 == null) return contactDtoList;

		if (senderId.equals(getUser().getId()) || receiverId.equals(getUser().getId())) {
			UsersContacts usersContacts = usersContactRepository.findByUser1AndUser2(user1, user2).orElse(null);
			if (usersContacts != null) {
				List<Contacts> contacts = new ArrayList<>(usersContacts.getContacts());
				contacts.sort(Comparator.comparing(Contacts::getSentDate));

				contactDtoList = contacts.stream().map(
								item -> {
									return new ContactDto(
													item.getId(),
													item.getSentDate(),
													item.getContent(),
													item.getIdSend(),
													Objects.requireNonNull(userRepository.findById(item.getIdSend()).orElse(null)).getName()
									);
								}
				).collect(Collectors.toList());
			}
			return contactDtoList;
		}
		return contactDtoList;
	}

	public void handleNotificationState(ChatMessage chatMessage, String roomIdContact) {
		Long senderId = Math.min(Long.parseLong(chatMessage.getSenderId()), Long.parseLong(roomIdContact));
		Long receiverId = Math.max(Long.parseLong(chatMessage.getSenderId()), Long.parseLong(roomIdContact));

		Users user1 = userRepository.findById(senderId).orElse(null);
		if (user1 == null) return;

		Users user2 = userRepository.findById(receiverId).orElse(null);
		if (user2 == null) return;

		if (chatMessage.getSenderId().equals(roomIdContact)) {
			UsersContacts usersContacts = usersContactRepository.findByUser1AndUser2(user1, user2).orElse(null);
			if (usersContacts != null) {
				usersContacts.setStateNotification(EStateNotification.ALL);
				usersContactRepository.save(usersContacts);
			}
		}
	}
}
