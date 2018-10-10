package com.vz.paas.core.support;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础业务逻辑接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @create 2018-10-10 13:49:21
 */
public interface Service<T> {

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param record 实体
     * @return 结果集
     */
    List<T> select(T record);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * @param key 主键
     * @return 结果实体
     */
    T selectByKey(Object key);

    /**
     * 列表结果集，select(null)方法能达到同样的结果
     * @return 结果集
     */
    List<T> list();

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，多个结果抛出异常，查询条件使用等号
     * @param record 实体
     * @return 结果实体
     */
    T selectOne(T record);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     * @param record 实体
     * @return 总数
     */
    int count(T record);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库的默认值
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 批量保存
     * @param list 实体对象集合
     * @return >1成功 0失败
     */
    @Transactional(rollbackFor = Exception.class)
    int batchSave(List<T> list);

    /**
     * 根据主键更新属性不为null的值
     * @param entity 实体
     * @return >1成功 0失败
     */
    int update(T entity);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     * @param record 实体
     * @return >1成功 0失败
     */
    int delete(T record);

    /**
     * 批量删除
     * @param list 实体集合
     * @return >1成功 0失败
     */
    @Transactional(rollbackFor = Exception.class)
    int batchDelete(List<T> list);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param key 主键
     * @return >1成功 0失败
     */
    int deleteByKey(Object key);

    /**
     * 查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
     * @param example 样本实体
     * @return 结果集
     */
    List<T> selectByExample(Object example);

    /**
     * 根据Example条件查询总数
     * @param example 样本实体
     * @return 总数
     */
    int countByExample(Object example);

    /**
     * 根据Example条件更新实体record包含的不是null的属性值
     * @param record 实体
     * @param example 样本实体
     * @return >1成功 0失败
     */
    int updateByExample(@Param("record") T record, @Param("example") Object example);

    /**
     * 根据Example条件删除数据
     * @param example 样本实体
     * @return >1成功 0失败
     */
    int deleteByExample(Object example);

    /**
     * 根据实体属性和RowBounds进行分页查询
     * @param record 实体
     * @param rowBounds 分页条件
     * @return 结果集
     */
    List<T> selectByRowBounds(T record, RowBounds rowBounds);

    /**
     * 根据Example条件和RowBounds进行分页查询
     * @param example 样本实体
     * @param rowBounds 分页条件
     * @return 结果集
     */
    List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds);
}
