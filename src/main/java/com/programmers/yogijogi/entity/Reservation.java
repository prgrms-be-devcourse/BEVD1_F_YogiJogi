package com.programmers.yogijogi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@Getter
@Where(clause = "del = false")
@SQLDelete(sql = "UPDATE reservation SET del = true WHERE id =?")
@DynamicInsert
public class Reservation {


    @Column(name = "del", columnDefinition = "boolean default false")
    private Boolean del;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private Review review;

    @Builder
    public Reservation(Long id, Room room, User user, LocalDate checkIn, LocalDate checkOut, Review review,boolean del) {
        this.id = id;
        this.room = room;
        this.user = user;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.review = review;
        this.del = del;
    }

    public Reservation setReview(Review review){
        this.review = review;
        review.setReservation(this);
        return this;
    }

    public void setRoom(Room room) {
        if(Objects.nonNull(this.room)) {
            this.room.getReservations().remove(this);
        }
        this.room = room;
    }

    public void setUser(User user) {
        if(Objects.nonNull(this.user)) {
            this.user.getReservations().remove(this);
        }
        this.user = user;
    }
}
