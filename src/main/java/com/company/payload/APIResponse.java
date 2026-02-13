package com.company.payload;

import java.time.LocalDateTime;

public class APIResponse<T> {

    private LocalDateTime localDateTime;
    private int status;
    private String message;
    private T data;

    public APIResponse(int status, String message, T data) {
        this.localDateTime = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;

    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
