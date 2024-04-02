package com.CST.ChatMessageWeb.payload.request;

import lombok.Data;

@Data
public class SignUpRequest {
	private String username;

	private String email;

	private String password;

	private String confirmPassword;

	private String role;
}
