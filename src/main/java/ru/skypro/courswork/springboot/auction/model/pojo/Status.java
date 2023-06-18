package ru.skypro.courswork.springboot.auction.model.pojo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    STARTED("Started"),
    STOPPED("Stopped"),
    CREATED("Created");
    private final String status;
}
