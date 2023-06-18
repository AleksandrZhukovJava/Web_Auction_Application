package ru.skypro.courswork.springboot.auction.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidView {
    private String bidderName;
    private ZonedDateTime bidDate;
}
