package ru.skypro.courswork.springboot.auction.model.mapper;

import ru.skypro.courswork.springboot.auction.model.dto.LotDTO;
import ru.skypro.courswork.springboot.auction.model.entity.Lot;
public class LotMapper {
    public static Lot toLot(LotDTO lotDTO) {
        return Lot.builder()
                .id(lotDTO.getId())
                .status(lotDTO.getStatus())
                .title(lotDTO.getTitle())
                .description(lotDTO.getDescription())
                .bidPrice(lotDTO.getBidPrice())
                .startPrice(lotDTO.getStartPrice()).build();
    }

    public static LotDTO fromLot(Lot lot) {
        return LotDTO.builder()
                .id(lot.getId())
                .status(lot.getStatus())
                .title(lot.getTitle())
                .description(lot.getDescription())
                .bidPrice(lot.getBidPrice())
                .startPrice(lot.getStartPrice()).build();
    }
}
