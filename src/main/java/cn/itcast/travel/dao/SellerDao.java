package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 通过sid查询 seller信息
     * @param sid
     * @return
     */
    Seller findSellerBySid(int sid);
}
