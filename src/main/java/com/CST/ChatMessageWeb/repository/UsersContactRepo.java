package com.CST.ChatMessageWeb.repository;

import com.CST.ChatMessageWeb.entity.UsersContacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersContactRepo extends JpaRepository<UsersContacts, Long> {
}
