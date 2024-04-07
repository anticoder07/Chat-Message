package com.CST.ChatMessageWeb.myController;

import com.CST.ChatMessageWeb.payload.dto.ContactDto;
import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
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

	@GetMapping("/api/get-user")
	public ResponseEntity<List<UserItemContactDto>> getUserItemContact() {
		List<UserItemContactDto> userList = userServices.getAllUserContact();

//		UserItemContactDto u = new UserItemContactDto(1L, "huong", "huong@gmail.com", "hello", "work");
//		List<UserItemContactDto> userList = new ArrayList<>(Collections.nCopies(6, u));

		return ResponseEntity.ok(userList);
	}

	@GetMapping("/api/get-user-current/{id}")
	public ResponseEntity<UserItemContactDto> getUserItemCurrentContact(@PathVariable Long id) {
		UserItemContactDto userList = userServices.getUserById(id);
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/api/get-chat")
	public ResponseEntity<List<ContactDto>> getChatUserById(@RequestParam("i") Long id) {
		List<ContactDto> userList = List.of();

		return ResponseEntity.ok(userList);
	}

	@PostMapping("/api/search")
	public ResponseEntity<List<UserItemContactDto>> searchUserItemContact(@RequestParam("s") String s) {
		List<UserItemContactDto> userItemContactDtoList = searchServices.searchAllUserEmail(s);

		return ResponseEntity.ok(userItemContactDtoList);
	}
}
