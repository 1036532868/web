package com.example.goods.controller;

import com.example.exception.CRUDException;
import com.example.goods.Service.CategoryService;
import com.example.goodsApi.domain.Category;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/1/31 21:26
 * @since 1.0.0
 */
@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
    * @Description TODO 返回所有 parentId 为参数数组 元素 的 Category对象
    * @param parentIds
    * @Return com.example.util.Result<java.util.List<com.example.goodsApi.domain.Category>>
    * @Author gong_da_kai
    * @Date 2021/2/1 8:56
    * @version 1.0.0
    * @since 1.0.0
    */
    @GetMapping("/selectByParentId")
    public Result<List<Category>> selectByParentId(int[] parentIds) throws CRUDException {

        List<Category> l = categoryService.selectByParentId(parentIds);

        return new Result<>(true, StatusCode.OK, "", l);
    }

}
