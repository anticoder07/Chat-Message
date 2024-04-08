package com.CST.ChatMessageWeb.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
	private Long id;

	private Date sentDate;

	private String message;

	private Long sendId;

	private String sendName;
}
