package com.CST.ChatMessageWeb.payload.dto;

import com.CST.ChatMessageWeb.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserItemContactDto {
	private Long id;

	private String userName;

	private String userEmail;

	private String lastMessage;

	private String state;

	public UserItemContactDto(Users users, String lastMessage) {
		this.id = users.getId();
		this.userName = users.getName();
		this.lastMessage = lastMessage;
		this.state = "work";
		this.userName = users.getUserEmail();
	}
}
