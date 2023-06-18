package ru.skypro.courswork.springboot.auction.exception.exceptions;

public class WrongLotStatus extends RuntimeException{
    public WrongLotStatus() {
        super("Status of lot CREATED or STOPPED");
    }
}

