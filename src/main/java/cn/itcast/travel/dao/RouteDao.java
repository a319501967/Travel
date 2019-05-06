package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 获取当前页显示的数据
     * @param startDisplay
     * @param displayCounts
     * @param cid
     * @param rename
     * @return
     */
    List<Route> findRoute(int startDisplay, int displayCounts, int cid, String rename);

    /**
     * 计算数据总数
     * @return
     * @param cid
     * @param rename
     */
    int findTotalDisplayCounts(int cid, String rename);

    /**
     * 查询详细信息
     * @param intRid
     * @return
     */
    Route findOne(int intRid);

    /**
     * 收藏
     *  通过rid集合查询List(Route)
     * @param ridList
     * @return
     */
    List<Route> findRouteByRid(List<Integer> ridList);
}
