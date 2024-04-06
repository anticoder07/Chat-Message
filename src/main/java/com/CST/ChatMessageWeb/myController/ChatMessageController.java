package com.CST.ChatMessageWeb.myController;

import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
import com.CST.ChatMessageWeb.services.userServices.UserServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ChatMessageController {
	private final UserServicesImpl userServices;

	@GetMapping("/api/get-user")
	public ResponseEntity<List<UserItemContactDto>> getAllUserItemContact() {
		UserItemContactDto u1 = new UserItemContactDto(1L, "huong", "hello world","hello world", "work");
		UserItemContactDto u2 = new UserItemContactDto(2L, "username2", "message2","hello world", "work2");
		UserItemContactDto u3 = new UserItemContactDto(3L, "username3", "message3","hello world", "work3");
		UserItemContactDto u4 = new UserItemContactDto(4L, "username4", "message4","hello world", "work4");
		UserItemContactDto u5 = new UserItemContactDto(5L, "username5", "message5","hello world", "work5");

		List<UserItemContactDto> userList = Arrays.asList(u1, u2, u3, u4, u5, u2, u3, u4, u5, u2, u3, u4, u5, u2, u3, u4, u5);

		return ResponseEntity.ok(userList);
	}
	@PostMapping("/api/search")
	public ResponseEntity<List<UserItemContactDto>> searchUserItemContact(@RequestParam("s") String s) {
		List<UserItemContactDto> userItemContactDtoList = userServices.searchAllUserEmail(s);

		return ResponseEntity.ok(userItemContactDtoList);
	}



}
