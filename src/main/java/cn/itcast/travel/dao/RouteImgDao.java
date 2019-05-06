package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 通过rid查询图片
     * @param intRid
     * @return
     */
    List<RouteImg> findImgByRid(int intRid);
}
