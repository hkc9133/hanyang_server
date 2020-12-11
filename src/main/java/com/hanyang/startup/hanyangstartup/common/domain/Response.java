package com.hanyang.startup.hanyangstartup.common.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Response {
    private String response;
    private String message;
    private Object data;
    private int code;

    public Response(String response, String message, Object data,int code) {
        this.response = response;
        this.message = message;
        this.data = data;
        this.code = code;
    }

}
