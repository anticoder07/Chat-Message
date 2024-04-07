package com.CST.ChatMessageWeb.repository;

import com.CST.ChatMessageWeb.entity.Contact.UsersContacts;
import com.CST.ChatMessageWeb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersContactRepo extends JpaRepository<UsersContacts, Long> {
	Optional<UsersContacts> findByUser1AndUser2(Users user1, Users user2);

	List<UsersContacts> findByUser1(Users users);

	List<UsersContacts> findByUser2(Users users);
}
