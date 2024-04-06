package com.CST.ChatMessageWeb.repository;

import com.CST.ChatMessageWeb.entity.Contact.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contacts, Long> {
//	List<Contacts> findAllByUserSend(Users userSend);
}
