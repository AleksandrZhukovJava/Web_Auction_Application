package ru.skypro.courswork.springboot.auction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Получить информацию о первом ставившем на лот")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Имя первого ставившего и дата первой ставки"),
            @ApiResponse(responseCode = "404", description = "Лот не найден")})
    public BidView getFirstBidById(@PathVariable("id") Integer id) {
        return bidService.getFirstBidById(id);

    }

    @GetMapping("/{id}/frequent")
    @Operation(summary = "Возвращает имя ставившего на данный лот наибольшее количество раз")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Имя ставившего наибольшее оличество раз и дата его последней ставки"),
            @ApiResponse(responseCode = "404", description = "Лот не найден")})
    public List<BidView> getNameWithBiggestAmountOfBid(@PathVariable("id") Integer id) {
        return bidService.getNameWithBiggestAmountOfBid(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает полную информацию о лоте с последним ставившим и текущей ценой")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Полный лот по идентификатору"),
            @ApiResponse(responseCode = "404", description = "Лот не найден")})
    public FullLot getFullLot(@PathVariable("id") Integer id) {
        return lotService.getFullLot(id);
    }

    @PostMapping("/{id}/start")
    @Operation(summary = "Начать торги по лоту")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Лот переведен в статус начато"),
            @ApiResponse(responseCode = "404", description = "Лот не найден")})
    public void startBiddingForLot(@PathVariable("id") Integer id) {
        lotService.startBiddingForLot(id);
    }

    @PostMapping("/{id}/bid")
    @Operation(summary = "Сделать ставку по лоту")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ставка создана"),
            @ApiResponse(responseCode = "400", description = "Лот в неверном статусе"),
            @ApiResponse(responseCode = "404", description = "Лот не найден")})
    public void createBid(@PathVariable("id") Integer id, @RequestBody BidName name) {
        bidService.createBid(name, id);
    }

    @PostMapping("/{id}/stop")
    @Operation(summary = "Остановить торги по лоту")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Лот перемещен в статус остановлен"),
            @ApiResponse(responseCode = "404", description = "Лот не найден")})
    public void stopBiddingForLot(@PathVariable("id") Integer id) {
        lotService.stopBiddingForLot(id);
    }

    @PostMapping
    @Operation(summary = "Создает новый лот")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Лот успешно создан"),
            @ApiResponse(responseCode = "400", description = "Лот прередан с ошибкой")})
    public void createLot(@RequestBody LotView lotView) {
        lotService.createLot(lotView);
    }

    @GetMapping
    @Operation(summary = "Получить все лоты, основываясь на фильтре статуса и номере страницы")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список лотов")})
    public List<LotDTO> getAllLotsPageableWithStatus(@RequestParam Status status, @RequestParam(required = false) Integer page) {
        return lotService.getLotsByStatusAndPage(status, page);
    }

    @GetMapping("/export")
    @Operation(summary = "Экспортировать все лоты в файл CSV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CSV Report"),
            @ApiResponse(responseCode = "404", description = "УБЕРИСЬ")})
    public ResponseEntity<Resource> getCSVLots() throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"lots.csv\"")
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .body(new ByteArrayResource(lotService.getCSVLots()));
    }
}