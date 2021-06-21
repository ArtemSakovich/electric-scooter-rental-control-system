package com.sakovich.scooterrental.model;

import com.sakovich.scooterrental.model.generic.AEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AEntity {

	@Builder
	public Role(Long id, String name) {
		super(id);
		this.name = name;
	}

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<User> users;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;
		Role role = (Role) o;
		return getName().equals(role.getName()) &&
				getUsers().equals(role.getUsers()) &&
				getId().equals(role.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getUsers(), getId());
	}

	@Override
	public String toString() {
		return "#" + getId() + " Role: " + getName() + "; Users: " + getUsers();
	}
}