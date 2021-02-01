package com.example.exception;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/1 8:58
 * @since 1.0.0
 */
public class CRUDException extends Exception{
    public CRUDException() {
    }

    public CRUDException(String message) {
        super(message);
    }
}
