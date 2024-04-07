package com.CST.ChatMessageWeb.entity.Contact;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Contacts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition="TEXT")
	private String content;

	private Date sentDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usersContact_id", nullable = false)
	private UsersContacts usersContact;

	public Contacts(String content, Date sentDate, UsersContacts usersContact) {
		this.content = content;
		this.sentDate = sentDate;
		this.usersContact = usersContact;
	}
}
