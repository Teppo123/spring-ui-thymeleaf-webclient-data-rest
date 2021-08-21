package com.example.springuithymeleaf.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springuithymeleaf.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Service
public class UserService {

	@Value("${url.getUsers}")
	private String urlGetUsers;

	@Value("${url.saveUser}")
	private String urlSaveUser;

	@Value("${url.deactivateUser}")
	private String urlDeactivateUser;

	private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

	@Autowired
	private WebClient localApiClient;

	public List<UserDTO> getUsers() {
		return this.localApiClient.get().uri(this.urlGetUsers).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<ResponseUsers>() {
				}).block(REQUEST_TIMEOUT).getEmbedded().getUsers();
	}

	public UserDTO saveUser(UserDTO user) {
		return this.localApiClient.post().uri(this.urlSaveUser).bodyValue(user).retrieve().bodyToMono(UserDTO.class)
				.block(REQUEST_TIMEOUT);
	}

	public Integer deactivateUserById(long id) {
		return this.localApiClient.put().uri(this.urlDeactivateUser + id).retrieve().bodyToMono(Integer.class)
				.block(REQUEST_TIMEOUT);
	}

	@Data
	private static final class ResponseUsers {
		@JsonProperty("_embedded")
		private EmbeddedUsers embedded;

		@Data
		private static final class EmbeddedUsers {
			private List<UserDTO> users = new ArrayList<>();
		}
	}

}
