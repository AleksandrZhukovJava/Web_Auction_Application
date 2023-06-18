package ru.skypro.courswork.springboot.auction.model.view;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FullLot {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
    private Long currentPrice;
    private BidView lastBid;

    public FullLot(Integer id, Status status, String title, String description, Integer startPrice, Integer bidPrice, Long currentPrice) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.bidPrice = bidPrice;
        this.currentPrice = currentPrice;
    }
}
