package com.example.user.controller;

import com.example.exception.CRUDException;
import com.example.user.service.UserService;
import com.example.userApi.domain.User;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/24 18:07
 * @since 1.0.0
 *
 * 暂时用不上shiro
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password, HttpServletRequest request) throws CRUDException {
        request.getSession().setAttribute("user", null);
        User user = userService.login(username, password);

        request.getSession().setAttribute("user", user);
        System.err.println(request.getSession().getId());
        return new Result(true, StatusCode.OK, "", user);
    }

    @GetMapping("/logout")
    @ResponseBody
    public Result logout(HttpSession session){
        session = null;

        return new Result();
    }

    @GetMapping("/get")
    @ResponseBody
    public Result getCurrentUser(HttpSession session){
        return new Result(true, StatusCode.OK, "", session.getAttribute("user"));
    }
}
