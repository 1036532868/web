package com.example.order.handler;

import com.example.exception.CRUDException;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/1 9:00
 * @since 1.0.0
 */
@RestControllerAdvice
public class CRUDExceptionHandler {

    @ExceptionHandler(CRUDException.class)
    public Result crudExceptionHandler(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());

    }

    @ExceptionHandler
    public Result handleAll(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());

    }
}
