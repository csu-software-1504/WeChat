package cn.csu.software.service;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

import cn.csu.software.dao.UserDao;
import cn.csu.software.entity.User;
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
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public User getUserByTelAndPwd(String tel, String password) {
        logger.info("------tel={}, password={}", tel, password);
        User user = userDao.getUserByTelAndPassword(tel, password);
        logger.info("------user detail,uid={}", user.getUid());
        return user;
    }

    public void addUser() {
        User u = new User();
        u.setTel("1");
        u.setPassword("1");
        u.setSex(1);
        u.setStatus(1);
        u.setPic("1");
        u.setUsername("1");
        Date date = new Date(System.currentTimeMillis());
        u.setCreateDate(date);
        userDao.addUser(u);
    }
}
