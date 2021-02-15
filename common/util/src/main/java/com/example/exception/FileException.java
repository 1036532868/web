package com.example.exception;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/4 12:01
 * @since 1.0.0
 */
public class FileException extends CRUDException{
    public FileException() {
    }

    public FileException(String message) {
        super(message);
    }
}
