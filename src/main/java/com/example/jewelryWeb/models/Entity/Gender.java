package com.example.jewelryWeb.models.Entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    UNISEX("unisex");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}