package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 获取当前页显示的数据
     *
     * @param startDisplay
     * @param displayCounts
     * @param cid
     * @param rename
     * @return
     */
    @Override
    public List<Route> findRoute(int startDisplay, int displayCounts, int cid, String rename) {
        String sql = "select * from tab_route where 1= 1";
        StringBuilder sb = new StringBuilder(sql);
        List param = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            param.add(cid);
        }
        if (rename != null &&!rename.equals("null")) {
            sb.append(" and rname like ?");
//            会自动识别为字符串不需要添加''
            param.add("%" + rename + "%");
        }
        sb.append(" limit ? , ?");
        param.add(startDisplay);
        param.add(displayCounts);
        sql=sb.toString();

        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), param.toArray());

    }

    /**
     * 计算数据总数
     *
     * @param cid
     * @param rename
     * @return
     */
    @Override
    public int findTotalDisplayCounts(int cid, String rename) {
        String sql = "SELECT COUNT(*) FROM tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List param = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            param.add(cid);
        }
        if (rename != null&&!rename.equals("null") ) {
            sb.append(" and rname like ?");
            param.add("%" + rename + "%");
        }
        sql=sb.toString();
        Integer integer = template.queryForObject(sql, Integer.class, param.toArray());
        return integer;
    }

    /**
     * 查询详细信息
     * @param intRid
     * @return
     */
    @Override
    public Route findOne(int intRid) {
        String sql="select * from tab_route where rid = ?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), intRid);
        return route;
    }

    /**
     * 通过rid查询Route集合
     * @param ridList
     * @return
     */
    @Override
    public List<Route> findRouteByRid(List<Integer> ridList) {
        String sql="Select * from tab_route where 1 = 1";
//        创建StringBuilder类拼接字符串
        StringBuilder builder = new StringBuilder(sql);
        builder.append(" and rid = ?");
        for (int i = 0; i <ridList.size()-1 ; i++) {
            builder.append(" or rid = ?");
        }
        sql=builder.toString();
//        传入参数 list转换成Array
        List<Route> query = null;
        try {
            query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), ridList.toArray());
        } catch (DataAccessException e) {

        }
        return query;
    }

}
