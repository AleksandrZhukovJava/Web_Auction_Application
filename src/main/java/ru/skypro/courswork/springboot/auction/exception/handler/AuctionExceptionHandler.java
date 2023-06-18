package ru.skypro.courswork.springboot.auction.exception.handler;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.courswork.springboot.auction.exception.exceptions.BidsNotFound;
import ru.skypro.courswork.springboot.auction.exception.exceptions.LotNotFound;
import ru.skypro.courswork.springboot.auction.exception.exceptions.WrongLotStatus;

@RestControllerAdvice
@AllArgsConstructor
public class AuctionExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(AuctionExceptionHandler.class);
    @ExceptionHandler(value = {BidsNotFound.class,LotNotFound.class})
    public ResponseEntity<?> handAuctionExceptionHandler(Exception exception){
        exception.printStackTrace();
        logger.error(exception.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handException(WrongLotStatus wrongStatusOfLot){
        wrongStatusOfLot.printStackTrace();
        logger.error(wrongStatusOfLot.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON parse error");
    }
    @ExceptionHandler
    public ResponseEntity<?> handException(Exception exception){
        exception.printStackTrace();
        logger.error(exception.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
