package com.CST.ChatMessageWeb.model;

import lombok.Data;

@Data
public class ChatMessage {
	private MessageType type;

	private String content;

	private String sender;

	private Long senderId;

	private Long receiverId;

	public enum MessageType {
		CHAT,
		JOIN,
		LEAVE
	}

}