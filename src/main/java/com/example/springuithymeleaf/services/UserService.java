package com.example.springuithymeleaf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springuithymeleaf.dto.UserDTO;

import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Value("${url.getUsers}")
	private String urlGetUsers;

	@Value("${url.saveUser}")
	private String urlSaveUser;

	@Value("${url.deactivateUser}")
	private String urlDeactivateUser;

	@Autowired
	private WebClient localApiClient;

	public Mono<CollectionModel<UserDTO>> getUsers() {
		return this.localApiClient.get().uri(this.urlGetUsers).accept(MediaTypes.HAL_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<CollectionModel<UserDTO>>() {
				});
	}

	public Mono<UserDTO> saveUser(UserDTO user) {
		return this.localApiClient.post().uri(this.urlSaveUser).bodyValue(user).retrieve().bodyToMono(UserDTO.class);
	}

	public Mono<UserDTO> deactivateUserById(long id) {
		return this.localApiClient.patch()
				.uri(uriBuilder -> uriBuilder.pathSegment(this.urlDeactivateUser, "{id}").build(id))
				.bodyValue(UserDTO.builder().id(id).deactivated(true).build()).retrieve().bodyToMono(UserDTO.class);
	}

}
