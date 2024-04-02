package com.CST.ChatMessageWeb.myController;

import com.CST.ChatMessageWeb.payload.dto.UserItemContactDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ChatMessageController {
//	public List<UserItemContactDto> getAllUserItemContact() {
//
//	}
}
