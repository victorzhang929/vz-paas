package com.vz.paas.core.support;

import java.util.List;

import com.vz.paas.base.exception.BusinessException;
import com.vz.paas.zk.generator.IncrementIdGenerator;
import com.vz.paas.zk.generator.UniqueIdGenerator;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
 * 基础抽象业务类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 15:13:05
 */
public abstract class BaseService<T> implements Service<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<T> list() {
        return mapper.selectAll();
    }

    @Override
    public T selectOne(T record) {
        return mapper.selectOne(record);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int count(T record) {
        return mapper.selectCount(record);
    }

    @Override
    public int save(T record) {
        return mapper.insertSelective(record);
    }

    @Override
    public int batchSave(List<T> list) {
        int result = 0;
        for (T record : list) {
            int count = mapper.insertSelective(record);
            if (count < 1) {
                logger.error("保存数据失败");
                throw new BusinessException("保存数据失败");
            }
            result += count;
        }
        return result;
    }

    @Override
    public int update(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(T record) {
        return mapper.delete(record);
    }

    @Override
    public int deleteByKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int batchDelete(List<T> list) {
        int result = 0;
        for (T record : list) {
            int count = mapper.delete(record);
            if (count < 1) {
                logger.error("删除数据失败");
                throw new BusinessException("删除诗句失败");
            }
            result += count;
        }
        return result;
    }

    @Override
    public int countByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    @Override
    public int updateByExample(T record, Object example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int deleteByExample(Object example) {
        return mapper.deleteByPrimaryKey(example);
    }

    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return mapper.selectByRowBounds(record, rowBounds);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return mapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    protected long generateId() {
        return UniqueIdGenerator.getInstance(IncrementIdGenerator.getServiceId()).nextId();
    }
}
