package cn.csu.software.dao;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

import cn.csu.software.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @title : cn.csu.software.dao
 * @author : oranges
 * @date : 2019/10/19 21:18
 * @description : 
 */
@Repository
public interface UserDao {

    public void addUser(@Param("user") User user);

//    public void deleteUserByUid(@Param("uid") int uid);
//
//    public void changeUserInfo(@Param("user") User user);

    public User getUserByTelAndPassword(@Param("tel") String tel, @Param("password") String password);
}
