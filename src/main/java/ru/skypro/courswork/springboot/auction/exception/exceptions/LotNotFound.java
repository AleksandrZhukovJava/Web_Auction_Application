package ru.skypro.courswork.springboot.auction.exception.exceptions;

public class LotNotFound extends RuntimeException{
    public LotNotFound() {
        super("Lot with this ID was not found");
    }
}
