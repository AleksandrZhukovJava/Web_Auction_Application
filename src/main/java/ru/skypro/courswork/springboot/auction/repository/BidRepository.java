package ru.skypro.courswork.springboot.auction.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.skypro.courswork.springboot.auction.model.entity.Bid;
import ru.skypro.courswork.springboot.auction.model.view.BidView;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends CrudRepository<Bid,Integer> {
    @Query("SELECT new ru.skypro.courswork.springboot.auction.model.view.BidView (b.name , b.time) " +
            "FROM Bid b WHERE b.lot.id = :id AND b.time = (SELECT min(b.time) FROM Bid b WHERE b.lot.id = :id)")
    Optional<BidView> getFirstBidById(@Param("id") Integer id);
    @Query("SELECT new ru.skypro.courswork.springboot.auction.model.view.BidView (b.name , b.time) " +
            "FROM Bid b WHERE b.lot.id = :id AND b.time = (SELECT max(b.time) FROM Bid b WHERE b.lot.id = :id)")
    Optional<BidView> getLastBidById(@Param("id") Integer id);
    @Query("SELECT new ru.skypro.courswork.springboot.auction.model.view.BidView (b.name , min(b.time))" +
            " FROM Bid b WHERE b.lot.id = :id GROUP BY b.name HAVING count(b.lot.id) = (SELECT count(b.lot.id) AS cou" +
            " FROM Bid b WHERE b.lot.id = :id GROUP BY b.name ORDER BY cou DESC LIMIT 1)")
    List<BidView> getNameWithBiggestAmountOfBid(@Param("id") Integer id);
}
