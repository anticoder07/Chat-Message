package com.CST.ChatMessageWeb.myController;

import com.CST.ChatMessageWeb.payload.dto.ChatMessage;
import com.CST.ChatMessageWeb.services.ChatMessageServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
	private final SimpMessageSendingOperations messagingTemplate;

	private final ChatMessageServices chatMessageServices;

	@GetMapping("/chat-message")
	public String chatMessage() {
		return "chatMessage";
	}

	@MessageMapping("/chat/{roomId}/sendMessage")
	public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
		if (chatMessageServices.saveMessage(chatMessage, roomId)) {
			messagingTemplate.convertAndSend(format("/channel/" + roomId), chatMessage);
		}
	}

	@MessageMapping("/chat/{roomId}/addUser")
	public void addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage,
											SimpMessageHeaderAccessor headerAccessor) {
		String currentRoomId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("room_id", roomId);
		if (currentRoomId != null) {
			ChatMessage leaveMessage = new ChatMessage();
			leaveMessage.setType(ChatMessage.MessageType.LEAVE);
			leaveMessage.setSender(chatMessage.getSender());
			messagingTemplate.convertAndSend(format("/channel/" + currentRoomId), leaveMessage);
		}
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		messagingTemplate.convertAndSend(format("/channel/" + roomId), chatMessage);
	}
}
