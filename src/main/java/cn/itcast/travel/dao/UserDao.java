package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * 用户注册
 */
public interface UserDao {


    /**
     * 根据用户名查询
      * @param username
     * @return
     */
    User findByUser(String username);

    /**
     * 注册
     * @param user
     */
    void registerUser(User user);

    /**
     * 通过code查询
      * @param code
     * @return
     */
    User findCode(String code);

    /**
     * 通过唯一激活码进行修改激活状态
     * @param code
     */
    void updateStatus(String code);

    /**
     * 根据用户密码密码查询
     * @param username
     * @param password
     * @return
     */
    User findUserNameAndPassWord(String username, String password);
}
