package com.sakovich.scooterrental.model;

import com.sakovich.scooterrental.model.enums.SubscriptionStatus;
import com.sakovich.scooterrental.model.generic.AEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
public class Subscription extends AEntity {

    @Builder
    public Subscription(Long id, Double price, Double balance) {
        super(id);
        this.price = price;
        this.balance = balance;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    private Double price;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "discount_percentage")
    private Float discountPercentage;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Transient
    public SubscriptionStatus getStatus() {
        return balance > 0 ? SubscriptionStatus.ACTIVE : SubscriptionStatus.CLOSED;
    }

    @Transient
    public Float getCoefficient() {
        return 100 / (100 + getDiscountPercentage());
    }

    public Double getBalance() {
        return Math.ceil(balance * Math.pow(10, 2)) / Math.pow(10, 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription subscription = (Subscription) o;
        return getUser().equals(subscription.getUser()) &&
                getPrice().equals(subscription.getPrice()) &&
                getBalance().equals(subscription.getBalance()) &&
                getDiscountPercentage().equals(subscription.getDiscountPercentage()) &&
                getStatus().equals(subscription.getStatus()) &&
                getId().equals(subscription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getPrice(), getBalance(), getDiscountPercentage(), getStatus(), getId());
    }

    @Override
    public String toString() {
        return "#" + getId() + " Subscription. Price: " + getPrice() + "; Balance: " + getBalance() +
                "; User: " + getUser() + " DiscountPercentage: " + getDiscountPercentage() + "; Status: " +
                getStatus();
    }
}