package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
    /**
     * 查询数据库 获取相应的Favorite对象
     *
     * @param rid
     * @param uid
     * @return
     */
    Favorite isFavorite(int rid, int uid);

    /**
     * 查询收藏次数
     * @param intRid
     * @return
     */
    int findFavoriteCountsByRid(int intRid);

    /**
     * 添加收藏功能
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);

    /**
     * 通过uid获取rid集合
     * @param uid
     * @return
     */
    List<Integer> findCidByUid(int uid);


}
