package ru.skypro.courswork.springboot.auction.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.courswork.springboot.auction.model.dto.LotDTO;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;
import ru.skypro.courswork.springboot.auction.model.view.BidName;
import ru.skypro.courswork.springboot.auction.model.view.BidView;
import ru.skypro.courswork.springboot.auction.model.view.FullLot;
import ru.skypro.courswork.springboot.auction.model.view.LotView;
import ru.skypro.courswork.springboot.auction.service.bid.BidService;
import ru.skypro.courswork.springboot.auction.service.lot.LotService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lot")
@AllArgsConstructor
public class LotController {
    private final LotService lotService;
    private final BidService bidService;
    @GetMapping("/{id}/first")
    @Tag(name = "Lot info")
    public BidView getFirstBidById(@PathVariable("id") Integer id) {
        return bidService.getFirstBidById(id);

    }
    @GetMapping("/{id}/frequent")
    @Tag(name = "Lot info")
    public List<BidView> getNameWithBiggestAmountOfBid(@PathVariable("id") Integer id) {
        return bidService.getNameWithBiggestAmountOfBid(id);
    }
    @GetMapping("/{id}")
    @Tag(name = "Lot info")
    public FullLot getFullLot(@PathVariable("id") Integer id) {
        return lotService.getFullLot(id);
    }
    @PostMapping("/{id}/start")
    @Tag(name = "Change bid status")
    public void startBiddingForLot(@PathVariable("id") Integer id) {
        lotService.startBiddingForLot(id);
    }
    @PostMapping("/{id}/bid")
    @Tag(name = "Do bid for lot")
    public void createBid(@PathVariable("id") Integer id, @RequestBody BidName name) {
        bidService.createBid(name, id);
    }
    @PostMapping("/{id}/stop")
    @Tag(name = "Change bid status")
    public void stopBiddingForLot(@PathVariable("id") Integer id) {
        lotService.stopBiddingForLot(id);
    }
    @PostMapping
    @Tag(name = "Create lot")
    public void createLot(@RequestBody LotView lotView) {
        lotService.createLot(lotView);
    }
    @GetMapping
    @Tag(name = "Lot info")
    public List<LotDTO> getAllLotsPageableWithStatus(@RequestParam Status status, @RequestParam(required = false) Integer page) {
        return lotService.getLotsByStatusAndPage(status, page);
    }
    @GetMapping("/export")
    @Tag(name = "Lot info")
    public ResponseEntity<Resource> getCSVLots() throws IOException{
        String fileName = "lots.csv";
        Resource resource = new ByteArrayResource(lotService.getCSVLots());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE,"text/csv")
                .body(resource);
    }
}
