package com.vz.paas.zk.generator;

/**
 * ID生成器接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 15:29:46
 */
public interface IdGenerator {

    /**
     * 生成下一个ID
     * @return 下个ID
     */
    Long nextId();
}
