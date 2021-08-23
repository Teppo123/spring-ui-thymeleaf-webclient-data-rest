package com.example.springuithymeleaf.services;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springuithymeleaf.dto.UserDTO;

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
		return this.localApiClient.get().uri(this.urlGetUsers).accept(MediaTypes.HAL_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<CollectionModel<UserDTO>>() {
				}).block(REQUEST_TIMEOUT).getContent().stream().collect(Collectors.toList());
	}

	public UserDTO saveUser(UserDTO user) {
		return this.localApiClient.post().uri(this.urlSaveUser).bodyValue(user).retrieve().bodyToMono(UserDTO.class)
				.block(REQUEST_TIMEOUT);
	}

	public Integer deactivateUserById(long id) {
		return this.localApiClient.put().uri(this.urlDeactivateUser + id).retrieve().bodyToMono(Integer.class)
				.block(REQUEST_TIMEOUT);
	}

}
