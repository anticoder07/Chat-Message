package com.CST.ChatMessageWeb.myController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class restControllerChatMsg {

	@GetMapping("chat-message")
	public String chatMessagePage(){
		return "chatMessage";
	}
}
