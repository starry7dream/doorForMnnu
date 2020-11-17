package com.cj.dao;

import com.cj.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserDao {
    //所有用户
    List<User> findAll();

    /***
     * 新增用户 返回Id 使用ID登录
     * @param user
     * @return
     */
    int addUser(User user);

    User getUserById(Integer id);

    int updateUser(User user);

    List<User> findUserByName(String  name);

    int  updateUserIP(Integer id,String ip);

    int deleteUser(Integer id);

    User checkLogin(String username,String password);

}
