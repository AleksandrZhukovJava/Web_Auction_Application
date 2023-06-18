package ru.skypro.courswork.springboot.auction.service.bid;

import org.springframework.web.bind.annotation.PathVariable;
import ru.skypro.courswork.springboot.auction.model.view.BidName;
import ru.skypro.courswork.springboot.auction.model.view.BidView;

import java.util.List;

public interface BidService {
    BidView getFirstBidById(Integer id);
    List<BidView> getNameWithBiggestAmountOfBid(@PathVariable("id") Integer id);
    void createBid(BidName bid, Integer id);
}
