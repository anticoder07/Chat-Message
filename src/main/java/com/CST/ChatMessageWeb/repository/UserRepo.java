package com.CST.ChatMessageWeb.repository;

import com.CST.ChatMessageWeb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
	Optional<Users> findByUserEmail(String userEmail);

	Boolean existsByUserEmail(String userEmail);

	@Query("""
					select u from Users u \s
					where (u.userEmail like concat('%', ?1, '%'))\s
					""")
	List<Users> searchAllByUserEmail(String userEmail);
}
