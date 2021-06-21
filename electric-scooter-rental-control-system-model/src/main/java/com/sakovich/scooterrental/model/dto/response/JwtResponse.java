package com.sakovich.scooterrental.model.dto.response;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String email;
	private String role;
	private String name;
	private String surname;
	private Integer age;

	public JwtResponse(String token, Long id, String email, String role, String name, String surname, Integer age) {
		this.token = token;
		this.id = id;
		this.email = email;
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.age = age;
	}
}
