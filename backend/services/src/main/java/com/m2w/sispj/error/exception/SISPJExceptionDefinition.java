package com.m2w.sispj.error.exception;

import org.springframework.http.HttpStatus;

public enum SISPJExceptionDefinition implements ExceptionDefinition {
    BAD_REQUEST("Bad request made, parameters not recognized", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_EXISTS("Cliente não existe!", HttpStatus.BAD_REQUEST),
    CUSTOMER_DUPLICATED_EMAIL("Já existe um cliente com o email cadastrado!", HttpStatus.BAD_REQUEST),
    CUSTOMER_DUPLICATED_NIF("Já existe um cliente com o NIF cadastrado!", HttpStatus.BAD_REQUEST),
    CUSTOMER_DUPLICATED_NISS("Já existe um cliente com o NISS cadastrado!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS("Usuário não existe!", HttpStatus.BAD_REQUEST),
    USER_DUPLICATED_NAME("Já existe um usuário com este nome cadastrado!", HttpStatus.BAD_REQUEST),
    USER_DUPLICATED_EMAIL("Já existe um usuário com este email cadastrado!", HttpStatus.BAD_REQUEST),
    INCORRECT_USER_OR_PASSWORD("Email ou password incorreto(s)!", HttpStatus.BAD_REQUEST),
    PROCESS_ALREADY_STARTED("O processo já foi iniciado!", HttpStatus.BAD_REQUEST),
    PROCESS_ALREADY_FINISHED("O processo já foi finalizado!", HttpStatus.BAD_REQUEST),
    PROCESS_NOT_EXISTS("Serviço não existe!", HttpStatus.BAD_REQUEST),
    PROCESS_TYPE_NOT_EXISTS("Serviço não existe!", HttpStatus.BAD_REQUEST),
    PROCESS_TYPE_DUPLICATED_NAME("Já existe um serviço com o mesmo nome!", HttpStatus.BAD_REQUEST),
    PROCESS_TYPE_DUPLICATED_NAME_EDITION("Já existe um serviço com o mesmo nome em edição!", HttpStatus.BAD_REQUEST),
    PROCESS_TYPE_IS_USED("Serviço não pode ser removido pois está em uso!", HttpStatus.BAD_REQUEST),
    DOCUMENT_TYPE_NOT_EXISTS("Tipo de documento não existe!", HttpStatus.BAD_REQUEST),
    DOCUMENT_TYPE_DUPLICATED_NAME("Já existe um tipo de documento com o mesmo nome!", HttpStatus.BAD_REQUEST),
    DOCUMENT_TYPE_IS_USED("Tipo de documento não pode ser removido porque está associado a um serviço!", HttpStatus.BAD_REQUEST),
    PROCESS_STEP_TYPE_NOT_EXISTS("Tipo de etapa não existe!", HttpStatus.BAD_REQUEST),
    PROCESS_STEP_TYPE_DUPLICATED_NAME("Já existe um tipo de etapa com o mesmo nome!", HttpStatus.BAD_REQUEST),
    PROCESS_STEP_TYPE_IS_USED("Tipo de etapa não pode ser removido porque está associado a um serviço!", HttpStatus.BAD_REQUEST),
    BUDGET_NOT_EXISTS("Orçamento não existe!", HttpStatus.BAD_REQUEST),
    DOCUMENT_UPLOAD_ERROR("Erro ao fazer upload do documento!", HttpStatus.BAD_REQUEST),
    CUSTOMER_DELETE("Usuário não tem permissão para remover cliente! Por favor entre em contacto com o administrador!", HttpStatus.BAD_REQUEST),
    CONTACT_DUPLICATED_NAME("Já existe um contacto com este nome cadastrado!", HttpStatus.BAD_REQUEST),
    BUDGET_DELETE("Usuário não tem permissão para remover orçamento! Por favor entre em contacto com o administrador!", HttpStatus.BAD_REQUEST),
    ;

    private String exceptionMessage;
    private HttpStatus statusCode;

    SISPJExceptionDefinition(String exceptionMessage, HttpStatus statusCode){
        this.exceptionMessage = exceptionMessage;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.exceptionMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return this.name();
    }

}
