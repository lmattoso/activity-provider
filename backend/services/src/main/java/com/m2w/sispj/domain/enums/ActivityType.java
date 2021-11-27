package com.m2w.sispj.domain.enums;

import lombok.Getter;

@Getter
public enum ActivityType {
    CUSTOMER_CREATED("Cliente criado"),
    CUSTOMER_UPDATED("Cliente alterado"),
    NOTE_CREATED("Nota Adicionada"),
    NOTE_UPDATED("Nota Alterada"),
    NOTE_DELETED("Nota Removida"),
    PROVISION_CREATED("Provisão Adicionada"),
    PROVISION_UPDATED("Provisão Alterada"),
    PROVISION_DELETED("Provisão Removida"),
    PAYMENT_CREATED("Pagamento Realizado"),
    PAYMENT_UPDATED("Pagamento Alterado"),
    PAYMENT_DELETED("Pagamento Removido"),
    DOCUMENT_CREATED("Documento Adicionado"),
    DOCUMENT_UPDATED("Documento Alterado"),
    DOCUMENT_DELETED("Documento Removido"),
    SERVICE_CREATED("Serviço criado"),
    SERVICE_STARTED("Serviço iniciado"),
    SERVICE_FINISHED("Serviço finalizado"),
    SERVICE_CANCELLED("Serviço cancelado"),
    SERVICE_DOCUMENT_VERIFIED("Documento verificado"),
    SERVICE_STEP_COMPLETED("Etapa completada")
    ;

    private String description;

    ActivityType(String description) {
        this.description = description;
    }
}