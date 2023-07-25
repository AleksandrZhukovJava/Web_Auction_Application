package ru.skypro.courswork.springboot.auction.exception.handler;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.skypro.courswork.springboot.auction.exception.exceptions.BidsNotFound;
import ru.skypro.courswork.springboot.auction.exception.exceptions.LotNotFound;
import ru.skypro.courswork.springboot.auction.exception.exceptions.WrongLotStatus;

@RestControllerAdvice
@AllArgsConstructor
public class AuctionExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(AuctionExceptionHandler.class);
    @ExceptionHandler(LotNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNoHandlerFoundException() {
        return "Lot not found";
    }

    @ExceptionHandler
    public ResponseEntity<?> handAuctionExceptionHandler(BidsNotFound bidsNotFound){
        bidsNotFound.printStackTrace();
        logger.error(bidsNotFound.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid not found");
    }


    @ExceptionHandler
    public ResponseEntity<?> handException(WrongLotStatus wrongStatusOfLot){
        wrongStatusOfLot.printStackTrace();
        logger.error(wrongStatusOfLot.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong status of lot");
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
