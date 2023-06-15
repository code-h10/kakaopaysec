package com.kakaopaysec.stock.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto<T> {

    private int code;
    private boolean status;
    private String message;
    private T data;


    public static <T> ResponseDto<T> success(int code, boolean status, String message) {
        return ResponseDto.<T>builder()
                .code(code)
                .status(status)
                .message(message)
                .build();
    }

    public static <T> ResponseDto<T> failure(String message) {
        return ResponseDto.<T>builder()
                .code(500)
                .status(false)
                .message(message)
                .build();
    }

    public static <T> ResponseDto<T> success(String message, T data) {
        return ResponseDto.<T>builder()
                .code(200)
                .status(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> success(T data) {
        return ResponseDto.<T>builder()
                .code(200)
                .status(true)
                .message("")
                .data(data)
                .build();
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
