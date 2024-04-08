package com.CST.ChatMessageWeb.myController;

import com.CST.ChatMessageWeb.payload.dto.ContactDto;
import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
import com.CST.ChatMessageWeb.services.ChatMessageServices;
import com.CST.ChatMessageWeb.services.SearchServices;
import com.CST.ChatMessageWeb.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ChatMessageController {
	private final SearchServices searchServices;

	private final UserServices userServices;

	private final ChatMessageServices chatMessageServices;

	@GetMapping("/api/get-user")
	public ResponseEntity<List<UserItemContactDto>> getUserItemContact() {
		List<UserItemContactDto> userList = userServices.getAllUserContact();

		return ResponseEntity.ok(userList);
	}

	@GetMapping("/api/get-user-current/{id}")
	public ResponseEntity<UserItemContactDto> getUserItemCurrentContact(@PathVariable Long id) {
		UserItemContactDto userList = userServices.getUserById(id);
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/api/get-chat")
	public ResponseEntity<List<ContactDto>> getChatUserById(@RequestParam("i") String id) {
		List<ContactDto> userList = chatMessageServices.getMessageById(id);

		return ResponseEntity.ok(userList);
	}

	@PostMapping("/api/search")
	public ResponseEntity<List<UserItemContactDto>> searchUserItemContact(@RequestParam("s") String s) {
		List<UserItemContactDto> userItemContactDtoList = searchServices.searchAllUserEmail(s);

		return ResponseEntity.ok(userItemContactDtoList);
	}
}
