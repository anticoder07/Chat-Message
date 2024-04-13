package com.CST.ChatMessageWeb.payload.dto;

import com.CST.ChatMessageWeb.entity.Contact.EStateNotification;
import com.CST.ChatMessageWeb.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserItemContactDto {
	private Long id;

	private String userName;

	private String userEmail;

	private String lastMessage;

	private String state;

	private String notification;

	public UserItemContactDto(Users users, String lastMessage, EStateNotification notification) {
		this.id = users.getId();
		this.userName = users.getName();
		this.lastMessage = lastMessage;
		this.state = "work";
		this.userEmail = users.getUserEmail();
		this.notification = notification.toString();
	}
}
