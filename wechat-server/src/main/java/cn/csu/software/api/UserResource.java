package cn.csu.software.api;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/20
 */

import cn.csu.software.dto.LoginUserDto;
import cn.csu.software.dto.RegisterUserDto;
import cn.csu.software.dto.ResultDto;
import cn.csu.software.service.UserService;
import cn.csu.software.util.JsonUtil;
import cn.csu.software.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @title : cn.csu.software.api
 * @author : oranges
 * @date : 2019/10/20 23:25
 * @description : 
 */

@Component
@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource implements LoggerUtil {

    @Autowired
    private UserService userService;

    @GET
    @Path("/login")
    public ResultDto loginFilter(LoginUserDto loginUserDto) {
        logger.info("loginUserDto={}", JsonUtil.toJson(loginUserDto));
        return userService.loginService(loginUserDto.getEmail(), loginUserDto.getPassword());
    }

    @POST
    @Path("/register")
    public ResultDto registerFiler(RegisterUserDto registerUserDto) {
        logger.info("registerUserDto={}", JsonUtil.toJson(registerUserDto));
        return userService.registerService(registerUserDto);
    }
}
