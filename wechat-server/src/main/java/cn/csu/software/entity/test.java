package cn.csu.software.entity;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/27
 */

import cn.csu.software.dto.LoginUserDto;
import cn.csu.software.dto.RegisterUserDto;
import cn.csu.software.util.JsonUtil;
import org.json.JSONObject;

/**
 * @title : cn.csu.software.entity
 * @author : oranges
 * @date : 2019/10/27 2:32
 * @description : 
 */
public class test {

    public static void main(String[] args) {
        LoginUserDto userDto = new LoginUserDto();
        userDto.setEmail("a");
        userDto.setPassword("a");
        userDto.setIp("a");
        JSONObject jsonObject = new JSONObject(userDto);
        System.out.println(jsonObject);

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUsername("a");
        registerUserDto.setPassword("a");
        registerUserDto.setEmail("a");
        registerUserDto.setIp("a");
        registerUserDto.setSex(1);
        System.out.println(JsonUtil.toJson(registerUserDto));
    }
}
