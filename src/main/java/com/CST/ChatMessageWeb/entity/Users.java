package com.CST.ChatMessageWeb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String userEmail;

	private String password;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole role;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EState state;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<Token> tokens;

	@OneToMany(mappedBy = "user1", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<UsersContacts> sentContacts;

	@OneToMany(mappedBy = "user2", fetch = FetchType.LAZY,
					cascade = CascadeType.ALL)
	private List<UsersContacts> receivedContacts;

	public Users(String name, String userEmail, String password, String role, String state) {
		this.name = name;
		this.userEmail = userEmail;
		this.password = password;
		if (role.equals("admin"))
			setRole(ERole.ADMIN);
		else if (role.equals("user")) {
			setRole(ERole.USER);
		}
		if (state.equals("inactive")) {
			this.state = EState.INACTIVE;
		} else {
			this.state = EState.WORK;
		}
	}
}
