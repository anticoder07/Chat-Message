package com.CST.ChatMessageWeb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersContacts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user1_id", nullable = false)
	private Users user1;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user2_id", nullable = false)
	private Users user2;

	private Long idSend;

	@OneToMany(mappedBy = "usersContact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Contacts> contacts;
}

