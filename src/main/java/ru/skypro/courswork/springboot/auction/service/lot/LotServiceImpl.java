package ru.skypro.courswork.springboot.auction.service.lot;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skypro.courswork.springboot.auction.exception.exceptions.LotNotFound;
import ru.skypro.courswork.springboot.auction.model.dto.LotDTO;
import ru.skypro.courswork.springboot.auction.model.entity.Lot;
import ru.skypro.courswork.springboot.auction.model.mapper.LotMapper;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;
import ru.skypro.courswork.springboot.auction.model.view.FullLot;
import ru.skypro.courswork.springboot.auction.model.view.LotView;
import ru.skypro.courswork.springboot.auction.repository.BidRepository;
import ru.skypro.courswork.springboot.auction.repository.LotRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class LotServiceImpl implements LotService {
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    @Override
    public FullLot getFullLot(Integer id) {
        FullLot fullLot = lotRepository.getFullLotById(id).orElseThrow(LotNotFound::new);
        fullLot.setLastBid(bidRepository.getLastBidById(id).orElse(null));
        return fullLot;
    }

    @Override
    public void startBiddingForLot(Integer id) {
        if (bidRepository.existsById(id)) {
            lotRepository.changeStatusOfLot(Status.STARTED, id);
        } else {
            throw new LotNotFound();
        }

    }
    @Override
    public void stopBiddingForLot(Integer id) {
        if (bidRepository.existsById(id)) {
            lotRepository.changeStatusOfLot(Status.STOPPED, id);
        } else {
            throw new LotNotFound();
        }

    }
    @Override
    public void createLot(LotView lotView) {
        lotRepository.save(new Lot(
                Status.CREATED
                , lotView.getTitle()
                , lotView.getDescription()
                , lotView.getStartPrice()
                , lotView.getBidPrice()));
    }
    @Override
    public List<LotDTO> getLotsByStatusAndPage(Status status, Integer page) {
        return lotRepository.findAllLotByStatus(status, PageRequest.of(page == null || page < 0 ? 0 : page, 10))
                .stream()
                .map(LotMapper::fromLot)
                .toList();
    }

    @Override
    public byte[] getCSVLots() throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("id", "title", "status", "lastBidder", "currentPrice")
                .build();

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8), csvFormat)) {
            for (Object[] lotCSVView : lotRepository.getCSVLots()) {
                csvPrinter.printRecord(lotCSVView);
            }

            csvPrinter.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
