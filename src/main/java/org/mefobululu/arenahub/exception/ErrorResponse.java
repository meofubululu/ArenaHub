package org.mefobululu.arenahub.exception;

public class ErrorResponse {
    private Integer code;
    private String message;
    private Long timestamp;

    public ErrorResponse(
            Integer code,
            String message,
            Long timestamp
    ){
        this.code = code;
        this.message =message;
        this.timestamp=timestamp;
    }
    public Integer getCode(){return code;}
    public String getMessage(){return message;}
    public Long getTimestamp(){return timestamp;}
}
