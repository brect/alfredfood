package com.padawanbr.alfredfood.api.exeptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class CustomExceptionHandler {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String message;
    private OffsetDateTime timestamp;

    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {

        private String name;
        private String message;
    }

}
