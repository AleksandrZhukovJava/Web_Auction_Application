package ru.skypro.courswork.springboot.auction.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LotCSVView {
    private Long id;
    private String title;
    private Status status;
    private String lastBidder;
    private Long currentPrice;
}