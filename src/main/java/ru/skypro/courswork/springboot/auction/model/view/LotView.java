package ru.skypro.courswork.springboot.auction.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LotView {
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
}

