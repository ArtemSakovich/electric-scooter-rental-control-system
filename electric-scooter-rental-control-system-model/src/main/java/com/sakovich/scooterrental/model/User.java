package com.sakovich.scooterrental.model;

import com.sakovich.scooterrental.model.enums.SubscriptionStatus;
import com.sakovich.scooterrental.model.generic.AEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AEntity {

    @Builder
    public User(Long id, String email, Integer age) {
        super(id);
        this.email = email;
        this.age = age;
    }

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RideSession> rideSessions;

    public User(String email, String password, String name, String surname, Integer age, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) &&
                getAge().equals(user.getAge()) &&
                getPassword().equals(user.getPassword()) &&
                getName().equals(user.getName()) &&
                getSurname().equals(user.getSurname()) &&
                getRole().equals(user.getRole()) &&
                getSubscriptions().equals(user.getSubscriptions()) &&
                getRideSessions().equals(user.getRideSessions()) &&
                getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getName(), getSurname(), getAge(), getRole(),
                getRideSessions(), getSubscriptions(), getId());
    }

    @Override
    public String toString() {
        return "#" + getId() + " User: " + getName() + " " + getSurname() + "; Email: " + getEmail() +
                "; Age: " + getAge();
    }

    public List<Subscription> getActiveSubscriptions() {
        return getSubscriptions().stream()
                .filter(subscription -> subscription.getStatus().equals(SubscriptionStatus.ACTIVE))
                .collect(Collectors.toList());
    }
}