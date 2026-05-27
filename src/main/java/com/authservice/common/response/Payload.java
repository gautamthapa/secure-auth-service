package com.authservice.common.response;

import com.authservice.common.pagination.Pagination;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload<T> implements ApiResponse {

    private final boolean success = true;

    private T data;

    private String message;

    private Pagination pagination;

    public Payload() {
    }

    public static <T> Payload<T> success(T data) {
        Payload<T> payload = new Payload<>();
        payload.setData(data);
        return payload;
    }

    public static <T> Payload<T> success(String message) {
        Payload<T> payload = new Payload<>();
        payload.setMessage(message);
        return payload;
    }

    public static <T> Payload<T> success(T data, String message) {
        Payload<T> payload = new Payload<>();
        payload.setData(data);
        payload.setMessage(message);
        return payload;
    }

    public static <T> Payload<T> success(T data, Pagination pagination) {
        Payload<T> payload = new Payload<>();
        payload.setData(data);
        payload.setPagination(pagination);
        return payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}