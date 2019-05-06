package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();


    @Override
    public List<Category> findAll() {
//       1.查询redis数据库
//        1.1 实例化Jedis对象
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorySet = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = new ArrayList<>();
        if (categorySet == null || categorySet.size() == 0) {
//           1.2 如果没有该数据 则创建
            List<Category> categoryList = dao.findAll();
//            1.2.1 遍历集合 获取数据添加
            for (Category getCategory : categoryList) {
                jedis.zadd("category", getCategory.getCid(), getCategory.getCname());
            }
//            重新获取 封装
            for ( Tuple tuple : jedis.zrangeWithScores("category", 0, -1)) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                list.add(category);
            }

            return list;
        } else {

//          如果存在  Json 只能转换list和map 所以需要将set转换成list;
            for (Tuple tuple : categorySet) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                list.add(category);
            }
            return list;
        }

    }
}
