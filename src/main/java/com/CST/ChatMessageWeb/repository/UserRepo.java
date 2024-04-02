package com.CST.ChatMessageWeb.repository;

import com.CST.ChatMessageWeb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
	Optional<Users> findByUserEmail(String userEmail);

	Boolean existsByUserEmail(String userEmail);
}
