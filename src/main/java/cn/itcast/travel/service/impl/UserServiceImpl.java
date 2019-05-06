package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();


    /**
     * 查询是否存在用户名 注册
     */
    @Override
    public Boolean findByUser(String username, User user) {
        User findUser = userDao.findByUser(username);
//      如果为空 则证明可以注册
        if (findUser == null) {
//            添加激活状态和激活码
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());
            userDao.registerUser(user);
            String text = "<a href='http://127.0.0.1/travel/user/activate?code=" + user.getCode() + "'>点击激活呦！</a>";
            System.out.println(text);
            MailUtils.sendMail(user.getEmail(), text, "老子用java给你发个邮件");
            return true;
        } else {
            return false;
        }
    }


    /**
     * 校验激活码 激活
     * 如果非空 则 激活码存在
     */
    @Override
    public Boolean findCode(String code) {
        boolean flog = false;
        User user = userDao.findCode(code);
        if (user != null) {
//            非空则修改激活状态
            userDao.updateStatus(code);
            flog = true;
        }
        return flog;
    }

    @Override
    public User findUserNameAndPassWord(String username, String password) {
        return userDao.findUserNameAndPassWord(username, password);
    }
}
