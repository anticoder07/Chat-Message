package com.CST.ChatMessageWeb.payload.request;

import lombok.Data;

@Data
public class LogInRequest {
	private String userEmail;

	private String password;
}