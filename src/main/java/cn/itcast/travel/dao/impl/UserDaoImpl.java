package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUser(String username) {
        User user = null;
        try {
            String sql = "Select * from tab_user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void registerUser(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) " +
                "values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    /**
     * 通过code查询
     * @param code
     * @return
     */
    public User findCode(String code) {
        User user = null;
        try {
            String sql="select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {

        }
        return user;
    }
    /**
     * 通过唯一激活码进行修改激活状态
     * @param code
     */
    @Override
    public void updateStatus(String code) {
             String sql="update tab_user set status = 'Y' where  code = ?";
             template.update(sql,code);
    }

    @Override
    public User findUserNameAndPassWord(String username, String password) {
        User user = null;
        try {
            String sql = "Select * from tab_user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        } catch (DataAccessException e) {

        }
        return user;
    }
}
