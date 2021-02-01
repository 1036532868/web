package com.example.goods.handler;

import com.example.exception.CRUDException;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/1 9:00
 * @since 1.0.0
 */
@ControllerAdvice
public class CRUDExceptionHandler {

    @ExceptionHandler(CRUDException.class)
    public Result crudExceptionHandler(Throwable t){

        return new Result(false, StatusCode.ERROR, t.getMessage());

    }

    @ExceptionHandler
    public Result handleAll(Throwable t){

        return new Result(false, StatusCode.ERROR, t.getMessage());

    }
}
