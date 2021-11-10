package com.programmers.yogijogi.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Reservation> reservations = new ArrayList<>();

    @Builder
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setUser(this);
    }

    public User addReservations(List<Reservation> reservations) {
        reservations.forEach(
                this::addReservation
        );

        return this;
    }

    public void updateUser(String name) {
        this.name = name;
    }
}
