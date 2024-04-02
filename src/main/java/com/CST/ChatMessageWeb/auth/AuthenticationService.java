package com.CST.ChatMessageWeb.auth;

import com.CST.ChatMessageWeb.entity.ETokenType;
import com.CST.ChatMessageWeb.entity.Token;
import com.CST.ChatMessageWeb.entity.Users;
import com.CST.ChatMessageWeb.payload.reponse.AuthenticationResponse;
import com.CST.ChatMessageWeb.payload.request.LogInRequest;
import com.CST.ChatMessageWeb.payload.request.SignUpRequest;
import com.CST.ChatMessageWeb.repository.TokenRepo;
import com.CST.ChatMessageWeb.repository.UserRepo;
import com.CST.ChatMessageWeb.security.jwt.JwtUtils;
import com.CST.ChatMessageWeb.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final AuthenticationManager authenticationManager;

	private final UserRepo userRepository;

	private final TokenRepo tokenRepository;

	private final JwtUtils jwtUtils;

	private final PasswordEncoder passwordEncoder;

	public AuthenticationResponse logIn(LogInRequest logInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						logInRequest.getUserEmail(),
						logInRequest.getPassword()
		));
		var user = userRepository.findByUserEmail(logInRequest.getUserEmail()).orElseThrow();
		UserDetailsImpl userDetails = new UserDetailsImpl(user);
		var jwtToken = jwtUtils.generateToken(userDetails);
		var refreshToken = jwtUtils.generateRefreshToken(userDetails);
		revokeAllUserTokens(userDetails);
		saveUserToken(userDetails, jwtToken);
		return new AuthenticationResponse(
						jwtToken,
						refreshToken
		);
	}

	public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
		if (userRepository.existsByUserEmail(signUpRequest.getEmail())) {
			return null;
		}
		Users users = new Users(
						signUpRequest.getUsername(),
						signUpRequest.getEmail(),
						passwordEncoder.encode(signUpRequest.getPassword()),
						signUpRequest.getRole(),
						"inactive"
		);
		UserDetailsImpl userDetails = new UserDetailsImpl(users);
		userRepository.save(users);
		var jwtToken = jwtUtils.generateToken(userDetails);
		var refreshToken = jwtUtils.generateRefreshToken(userDetails);
		saveUserToken(userDetails, jwtToken);
		return new AuthenticationResponse(
						jwtToken,
						refreshToken
		);
	}

	private void saveUserToken(UserDetailsImpl userDetails, String jwtToken) {
		var token = Token.builder()
						.user(userDetails.getUsers())
						.token(jwtToken)
						.tokenType(ETokenType.BEARER)
						.expired(false)
						.revoked(false)
						.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(UserDetailsImpl userDetails) {
		var validUserToken = tokenRepository.findAllValidTokenByUser(userDetails.getUsers().getId());
		if (validUserToken.isEmpty())
			return;
		validUserToken.forEach(
						token -> {
							token.setExpired(true);
							token.setRevoked(true);
						}
		);
		tokenRepository.saveAll(validUserToken);
	}
}
