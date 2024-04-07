package com.CST.ChatMessageWeb.entity.Contact;

import com.CST.ChatMessageWeb.entity.Contact.Contacts;
import com.CST.ChatMessageWeb.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

	private Date lastContactTime;

	@OneToMany(mappedBy = "usersContact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Contacts> contacts;

	public UsersContacts(Users user1, Users user2, Long idSend) {
		this.user1 = user1;
		this.user2 = user2;
		this.idSend = idSend;
	}
}

