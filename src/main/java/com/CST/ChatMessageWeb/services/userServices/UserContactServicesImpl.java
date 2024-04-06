package com.CST.ChatMessageWeb.services.userServices;

import com.CST.ChatMessageWeb.entity.Contact.Contacts;
import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.entity.Contact.UsersContacts;
import com.CST.ChatMessageWeb.repository.ContactRepo;
import com.CST.ChatMessageWeb.repository.UserRepo;
import com.CST.ChatMessageWeb.repository.UsersContactRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContactServicesImpl {
	private final UsersContactRepo usersContactRepository;

	private final UserRepo usersRepository;

	private final ContactRepo contactRepository;

	public Optional<Long> getChatId(
					Long sendId, Long recipientId, String content
	) {
		Users user1 = usersRepository.findById(sendId).orElse(null);
		Users user2 = usersRepository.findById(recipientId).orElse(null);
		return usersContactRepository
						.findByUser1AndUser2(user1, user2)
						.map(UsersContacts::getId)
						.or(() -> {
							UsersContacts usersContacts = UsersContacts
											.builder()
											.user1(user1)
											.user2(user2)
											.idSend(sendId)
											.build();

							usersContactRepository.save(usersContacts);
							Contacts contacts = Contacts
											.builder()
											.content(content)
											.sentDate(new Date())
											.usersContact(usersContacts)
											.build();

							contactRepository.save(contacts);

							return Optional.of(usersContacts.getId());
						});
	}



}
