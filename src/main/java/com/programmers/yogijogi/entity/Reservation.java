package com.programmers.yogijogi.entity;

import com.programmers.yogijogi.entity.dto.ReservationDto;
import com.programmers.yogijogi.entity.dto.RoomDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@Getter
public class Reservation {

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
    public Reservation(Long id, Room room, User user, LocalDate checkIn, LocalDate checkOut, Review review) {
        this.id = id;
        this.room = room;
        this.user = user;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.review = review;
    }

    public void setReview(Review review){
        this.review = review;
        review.setReservation(this);
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
