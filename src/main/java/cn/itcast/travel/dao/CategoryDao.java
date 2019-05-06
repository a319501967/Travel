package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 获取所有tab_category数据
     * @return
     */
    List<Category> findAll();
}
