package com.example.file.controller;

import com.example.exception.FileException;
import com.example.file.util.FileUtil;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/4 11:52
 * @since 1. */
@RestController
public class FileController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws FileException {
        System.err.println("执行了");
        FileUtil fileUtil = new FileUtil();
        fileUtil.upload(file);

        System.err.println(fileUtil.getOldFileName());
        System.err.println(fileUtil.getGroupName());
        System.err.println(fileUtil.getUrl());

        return new Result<>(true, StatusCode.OK, "", fileUtil.getUrl());
    }
}
