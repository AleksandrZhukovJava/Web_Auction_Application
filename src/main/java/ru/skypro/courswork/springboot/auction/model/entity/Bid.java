package ru.skypro.courswork.springboot.auction.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Entity
@Table (name = "bid")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private ZonedDateTime time;
    @JoinColumn(name = "lot_id")
    @ManyToOne (cascade = CascadeType.DETACH)
    private Lot lot;
}
