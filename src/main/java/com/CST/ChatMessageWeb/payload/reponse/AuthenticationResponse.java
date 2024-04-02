package com.CST.ChatMessageWeb.payload.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	@JsonProperty("token")
	private String accessToken;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String userName;

	@JsonProperty("role")
	private String role;
}
