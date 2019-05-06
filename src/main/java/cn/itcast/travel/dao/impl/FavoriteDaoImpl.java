package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询数据库 获取相应的Favorite对象
     *
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite isFavorite(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);

        } catch (DataAccessException e) {

        }
        return favorite;
    }

    /**
     * 查询收藏次数
     *
     * @param intRid
     * @return
     */
    @Override
    public int findFavoriteCountsByRid(int intRid) {
        String sql = "select count(*) from tab_favorite where rid = ? ";
        int count = template.queryForObject(sql, Integer.class, intRid);
        return count;
    }

    /**
     * 添加收藏功能
     *
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values (?,?,?)";
        template.update(sql, rid, new Date(), uid);
    }

    /**
     * 通过uid获取rid集合
     * @param uid
     * @return
     */
    @Override
    public List<Integer> findCidByUid(int uid) {
        String sql = "select rid from tab_favorite where uid = ?";

//        获取集合形式结果
        List<Integer> ridList = null;
        try {
            ridList = template.query(sql, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                    int rid = resultSet.getInt("rid");
                    return rid;
                }
            },uid);
        } catch (DataAccessException e) {

        }
        return ridList;
    }



}
