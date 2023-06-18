package ru.skypro.courswork.springboot.auction.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;

import java.util.List;

@Entity
@Table (name = "lot")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column (name = "title")
    @Size (min = 3, message = "Min length 3")
    private String title;
    @Column (name = "description")
    @Size (min = 1, message = "Min length 1")
    private String description;
    @Column (name = "start_price")
    @Min(value = 1, message = "Min value 1")
    private Integer startPrice;
    @Column (name = "bid_price")
    @Min(value = 1, message = "Min value 1")
    private Integer bidPrice;
    @OneToMany (cascade = CascadeType.REMOVE)
    private List<Bid> bids;
}
