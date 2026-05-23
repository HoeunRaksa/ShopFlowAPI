package com.flowShop.spring.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage<T> {

    @Builder.Default
    private Integer code = 0;

    @Builder.Default
    private Integer status = 200;

    private String message;

    private T data;

    public static <T> ResultMessage<T> success(
            Integer code,
            String message,
            T data
    ) {

        return ResultMessage.<T>builder()
                .code(code)
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResultMessage<T> error(
            Integer code,
            String message
    ) {

        return ResultMessage.<T>builder()
                .code(code)
                .status(-1)
                .message(message)
                .build();
    }
}