package com.CST.ChatMessageWeb.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
	private Date sentDate;

	private String message;
}
