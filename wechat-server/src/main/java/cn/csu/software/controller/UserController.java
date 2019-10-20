package cn.csu.software.controller;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

import cn.csu.software.entity.User;
import cn.csu.software.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title : cn.csu.software.controller
 * @author : oranges
 * @date : 2019/10/19 21:39
 * @description : 
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public User getUser() {
        return userService.getUserByTelAndPwd("123", "123");
    }

    @RequestMapping("/user/add")
    public void addUser() {
        userService.addUser();
    }
}
