package org.mefobululu.arenahub.exception;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handler(BusinessException e){
        ErrorResponse response = new ErrorResponse(
                e.getStatus().value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<ErrorResponse> handleRedisException(
            RedisConnectionFailureException e){

        ErrorResponse response = new ErrorResponse(
                503,
                "排行榜服务暂时不可用",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
