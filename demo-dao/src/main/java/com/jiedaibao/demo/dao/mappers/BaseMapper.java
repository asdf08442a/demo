package com.jiedaibao.demo.dao.mappers;

import java.util.List;

/**
 * 基础数据访问接口
 *
 */
public interface BaseMapper<T> {

    /**
     * 单条插入
     *
     * @param t 实体
     */
    int insert(T t);

    /**
     * 批量插入
     *
     * @param list 实体列表
     */
    int batchInsert(List<T> list);

    /**
     * 单条更新
     *
     * @param t 实体
     */
    int update(T t);

    /**
     * 单条查询
     *
     * @param id 
     */
    T getById(int id);

}
