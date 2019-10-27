package cn.csu.software.service;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

import cn.csu.software.dao.UserDao;
import cn.csu.software.dto.RegisterUserDto;
import cn.csu.software.dto.ResultDto;
import cn.csu.software.entity.User;
import cn.csu.software.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @title : cn.csu.software.service
 * @author : oranges
 * @date : 2019/10/19 21:32
 * @description : 
 */
@Service
public class UserService implements LoggerUtil {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public ResultDto loginService(String email, String password) {
        User user = userDao.getUserByEmailAndPassword(email, password);
        if (null == user) {
            return ResultDto.FAIL("邮箱或密码错误，请重新输入");
        } else {
            return ResultDto.SUCCESS();
        }
    }

    public ResultDto registerService(RegisterUserDto registerUserDto) {
        int count = userDao.selectCountEmail(registerUserDto.getEmail());
        if (count == 0) {
            User user = new User();
            user.setEmail(registerUserDto.getEmail());
            user.setPassword(registerUserDto.getPassword());
            user.setUsername(registerUserDto.getUsername());
            user.setIp(registerUserDto.getIp());
            user.setSex(registerUserDto.getSex());
            user.setStatus(1);
            user.setCreateDate(new Date(System.currentTimeMillis()));
            user.setAvatar("默认头像");
            userDao.addUser(user);
            int uid = userDao.getUidByEmail(user.getEmail());
            logger.info("------uid={}", uid);
            userDao.createUserFriendList(uid);
            return ResultDto.SUCCESS("注册成功");
        } else {
            return ResultDto.FAIL("该邮箱已被注册");
        }
    }
}
