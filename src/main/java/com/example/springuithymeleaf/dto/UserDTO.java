package com.example.springuithymeleaf.dto;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

	private static final long serialVersionUID = 176712563459609446L;

	private long id;

	private String firstName;

	private String lastName;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("createdAt")
	private LocalDateTime creationTime;

	private boolean deactivated;

	@JsonIgnore
	public String getSelfUrl() {
		return Optional.ofNullable(this.getLink(LinkRelation.of("self")))
				.orElseThrow(() -> new IllegalArgumentException("User link not found")).map(Link::toUri)
				.map(URI::toString).get();
	}

}
