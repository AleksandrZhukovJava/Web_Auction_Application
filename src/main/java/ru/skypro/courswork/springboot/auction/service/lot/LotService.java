package ru.skypro.courswork.springboot.auction.service.lot;

import ru.skypro.courswork.springboot.auction.model.dto.LotDTO;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;
import ru.skypro.courswork.springboot.auction.model.view.FullLot;
import ru.skypro.courswork.springboot.auction.model.view.LotView;

import java.io.IOException;
import java.util.List;

public interface LotService {
    FullLot getFullLot(Integer id);
    void startBiddingForLot(Integer id);
    void stopBiddingForLot(Integer id);
    void createLot(LotView lotView);
    List<LotDTO> getLotsByStatusAndPage(Status status, Integer page);
    byte[] getCSVLots() throws IOException;
}
