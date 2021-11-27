package com.m2w.sispj.domain.enums;

import lombok.Getter;

@Getter
public enum Genre {
    F("Feminino"),
    M("Masculino");

    private String description;

    Genre(String description) {
        this.description = description;
    }
}
