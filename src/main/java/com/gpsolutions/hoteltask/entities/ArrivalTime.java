package com.gpsolutions.hoteltask.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.StringJoiner;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "arrival_time")
public class ArrivalTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in", nullable = false)
    private String checkIn;

    @Column(name = "check_out", nullable = false)
    private String checkOut;

    @Override
    public String toString() {
        return new StringJoiner(", ", ArrivalTime.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("checkIn='" + checkIn + "'")
                .add("checkOut='" + checkOut + "'")
                .toString();
    }
}
