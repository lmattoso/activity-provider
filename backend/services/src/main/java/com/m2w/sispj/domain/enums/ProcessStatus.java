package com.m2w.sispj.domain.enums;

import lombok.Getter;

@Getter
public enum ProcessStatus {
    NOT_STARTED("Não Iniciado", 1),
    IN_PROGRESS("Em Andamento", 2),
    FINISHED("Finalizado", 3),
    CANCELLED("Cancelado", 4)
    ;

    private final String description;
    private final int order;

    ProcessStatus(String description, int order) {
        this.description = description;
        this.order = order;
    }
}