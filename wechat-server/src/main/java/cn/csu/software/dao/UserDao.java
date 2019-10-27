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

    public int selectCountEmail(@Param("email") String email);

    public int addUser(@Param("user") User user);

    public void createUserFriendList(@Param("uid") int user);

    public void createUserGroupList(@Param("uid") int user);

//    public void deleteUserByUid(@Param("uid") int uid);
//
//    public void deleteUserByTel(@Param("uid") String uid);
//
//    public void updateUserInfo(@Param("user") User user);

    public User getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    public int getUidByEmail(@Param("email") String email);

//    public User getUserByUid(@Param("uid") int uid);
}
