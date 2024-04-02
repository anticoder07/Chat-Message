package com.CST.ChatMessageWeb.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationRestController {
	@GetMapping("log-in")
	public String loginPage() {
		return "logIn";
	}

	@GetMapping("sign-up")
	public String signUpPage() {
		return "signUp";
	}
}

