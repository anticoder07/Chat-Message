package com.CST.ChatMessageWeb.payload.dto;

import lombok.Data;

@Data
public class ChatMessage {

	public enum MessageType {
		CHAT,
		JOIN,
		LEAVE
	}

	private MessageType type;

	private String content;

	private String sender;

	private String senderId;
}
