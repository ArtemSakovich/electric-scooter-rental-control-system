package com.sakovich.scooterrental.web.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

	@Builder
	public UserDetailsImpl(Collection<GrantedAuthority> authorities, String email) {
		this.authorities = authorities;
		this.email = email;
	}

	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	@JsonIgnore
	private String password;
	private String name;
	private String surname;
	private Integer age;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String email, String password,
			Collection<? extends GrantedAuthority> authorities, String name, String surname, Integer age) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.name = name;
		this.surname = surname;
		this.age = age;
	}

	public static UserDetailsImpl build(User user) {
		List<Role> roles = new ArrayList<>();
		roles.add(user.getRole());
		List<GrantedAuthority> authorities = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getEmail(),
				user.getPassword(), 
				authorities,
				user.getName(),
				user.getSurname(),
				user.getAge());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Integer getAge() {
		return age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
