package com.CST.ChatMessageWeb.repository;

import com.CST.ChatMessageWeb.entity.Contacts;
import com.CST.ChatMessageWeb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepo extends JpaRepository<Contacts, Long> {
//	List<Contacts> findAllByUserSend(Users userSend);
}
