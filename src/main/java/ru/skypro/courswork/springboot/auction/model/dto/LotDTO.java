package ru.skypro.courswork.springboot.auction.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LotDTO {
    private Integer id;
    private Status status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
}
