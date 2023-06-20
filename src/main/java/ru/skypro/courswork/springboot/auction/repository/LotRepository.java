package ru.skypro.courswork.springboot.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.courswork.springboot.auction.model.entity.Lot;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;
import ru.skypro.courswork.springboot.auction.model.view.FullLot;

import java.util.List;
import java.util.Optional;

public interface LotRepository extends CrudRepository<Lot, Integer>, PagingAndSortingRepository<Lot, Integer> {

    @Query("select new ru.skypro.courswork.springboot.auction.model.view.FullLot (l.id, l.status,l.title,l.description,l.startPrice,l.bidPrice, (COUNT(b.lot) * l.bidPrice + l.startPrice)) FROM Lot l " +
            "LEFT JOIN Bid b ON b.lot = l WHERE l.id = :id GROUP BY l.id, l.status,l.title,l.description,l.startPrice,l.bidPrice, b.lot")
    Optional<FullLot> getFullLotById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Lot l SET l.status = :status WHERE l.id = :id")
    void changeStatusOfLot(@Param("status") Status status, @Param("id") Integer id);

    Page<Lot> findAllLotByStatus(@Param("status") Status status, Pageable pageable);
    @Query(value = "SELECT l.id, l.title, l.status, max_bid.name, (b.count * l.bid_price + l.start_price) AS current_price FROM lot l " +
            "LEFT JOIN (SELECT a.lot_id, COUNT(a.lot_id) AS count FROM bid a GROUP BY lot_id) b ON l.id = b.lot_id " +
            "LEFT JOIN (SELECT b.id, b.name, b.time, b.lot_id  FROM bid b " +
            "INNER JOIN ( SELECT lot_id, MAX(time) AS max_time FROM bid GROUP BY lot_id) max ON b.lot_id = max.lot_id AND b.time = max.max_time) max_bid ON l.id = max_bid.lot_id;", nativeQuery = true)
    List<Object[]> getCSVLots();

}