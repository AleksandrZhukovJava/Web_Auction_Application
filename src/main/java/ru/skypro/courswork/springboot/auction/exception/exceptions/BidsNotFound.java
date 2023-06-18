package ru.skypro.courswork.springboot.auction.exception.exceptions;

public class BidsNotFound extends RuntimeException{
    public BidsNotFound() {
        super("Bids not found");
    }
}
