package ru.skypro.courswork.springboot.auction.service.bid;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.skypro.courswork.springboot.auction.exception.exceptions.LotNotFound;
import ru.skypro.courswork.springboot.auction.exception.exceptions.WrongLotStatus;
import ru.skypro.courswork.springboot.auction.model.entity.Bid;
import ru.skypro.courswork.springboot.auction.model.entity.Lot;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;
import ru.skypro.courswork.springboot.auction.model.view.BidName;
import ru.skypro.courswork.springboot.auction.model.view.BidView;
import ru.skypro.courswork.springboot.auction.repository.BidRepository;
import ru.skypro.courswork.springboot.auction.repository.LotRepository;

import java.time.ZonedDateTime;
import java.util.List;
@AllArgsConstructor
@Service
public class BidServiceImpl implements BidService{
    private BidRepository bidRepository;
    private LotRepository lotRepository;
    @Override
    public BidView getFirstBidById(Integer id) {
        if (bidRepository.existsById(id)) {
            return bidRepository.getFirstBidById(id).orElse(null);
        } else {
            throw new LotNotFound();
        }
    }
    @Override
    public List<BidView> getNameWithBiggestAmountOfBid(@PathVariable("id") Integer id) {
        if (bidRepository.existsById(id)) {
            return bidRepository.getNameWithBiggestAmountOfBid(id);
        } else {
            throw new LotNotFound();
        }
    }
    @Override
    public void createBid(BidName bid, Integer id) {
        Lot lot = lotRepository.findById(id).orElseThrow(LotNotFound::new);
        if (!lot.getStatus().equals(Status.STARTED)) {
            throw new WrongLotStatus();
        }
        bidRepository.save(new Bid(null, bid.getName(), ZonedDateTime.now(), lot));
    }
}
