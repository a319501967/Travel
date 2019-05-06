package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     *  查询是否存在用户名 注册
     */
    Boolean findByUser(String username, User user);
    /**
     * 校验激活码 激活
     */
    Boolean findCode(String code);


    /**
     * 根据用户名密码查询
     * @param username
     * @param password
     * @return
     */
    User findUserNameAndPassWord(String username, String password);
}
