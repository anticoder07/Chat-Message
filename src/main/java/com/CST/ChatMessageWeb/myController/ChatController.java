package com.CST.ChatMessageWeb.myController;

import com.CST.ChatMessageWeb.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage,
														 SimpMessageHeaderAccessor headerAccessor) {
		// Add username in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}

	@MessageMapping("/user/{receiverId}")
	@SendTo("/queue/private")
	public ChatMessage sendPrivateMessage(@Payload ChatMessage chatMessage,
																				@DestinationVariable Long receiverId) {
		System.out.println(chatMessage);
		System.out.println(receiverId);
		chatMessage.setReceiverId(receiverId);
		return chatMessage;
	}

}
