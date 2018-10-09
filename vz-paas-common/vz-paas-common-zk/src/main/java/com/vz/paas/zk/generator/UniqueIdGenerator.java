package com.vz.paas.zk.generator;

/**
 * 分布式唯一ID生成器
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-09 17:51:54
 */
public class UniqueIdGenerator implements IdGenerator {

    /**
     * 开始使用该算法的时间为: 2017-01-01 00:00:00
     */
    private static final long START_TIME = 1483200000000L;

    /**
     * worker id的bit数，最多支持8192个App和host的组合
     * 即在N个服务器上每个服务器上部署M个项目，总共部署N*M=8192
     */
    private static final int APP_HOST_ID_BITS = 13;

    /**
     * 序列号，支持单节点最高1000*1024个并发
     */
    private static final int SEQUENCE_BITS = 10;

    /**
     * 最大的app host id, 8091
     */
    private static final long MAX_APP_HOST_ID = ~(1L << APP_HOST_ID_BITS);

    /**
     * 最大的序列号：1023
     */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    /**
     * app host编号的移位
     */
    private static final long APP_HOST_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 时间戳的移位
     */
    private static final long TIMESTAMP_LEFT_SHIFT = APP_HOST_ID_BITS + APP_HOST_ID_SHIFT;

    /**
     * 该项目的app host id，对应着为某台机器上的某个项目分配的serviceId(注意区分span中的serviceId)
     */
    private long appHostId;

    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 当前毫秒生成的序列
     */
    private long sequence = 0L;

    /**
     * 单例
     */
    private static volatile UniqueIdGenerator idGenerator = null;

    /**
     * 实例化
     * @param appHostId 项目位置
     * @return UniqueIdGenerator实例
     */
    public static UniqueIdGenerator getInstance(long appHostId) {
        if (idGenerator == null) {
            synchronized (UniqueIdGenerator.class) {
                if (idGenerator == null) {
                    idGenerator = new UniqueIdGenerator(appHostId);
                }
            }
        }
        return idGenerator;
    }

    private UniqueIdGenerator(long appHostId) {
        if (appHostId > MAX_APP_HOST_ID) {
            // zk分配的serviceId过大（基本小规模的公司不会出现这样的问题）
            throw new IllegalArgumentException(String.format("app host id wrong: %d", appHostId));
        }
        this.appHostId = appHostId;
    }

    @Override
    public Long nextId() {
        return this.genUniqueId();
    }

    /**
     * 生成唯一id的具体实现
     * @return 唯一id
     */
    private synchronized long genUniqueId() {
        long current = System.currentTimeMillis();

        if (current < lastTimestamp) {
            // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，出现问题返回-1
            return -1;
        }
        if (current == lastTimestamp) {
            // 如果当前生成ID的时间还是上次的时间，那么对sequence序列号进行+1
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == MAX_SEQUENCE) {
                // 当前毫秒生成的序列书已经大于最大值，那么阻塞到下个毫秒再获取新的时间戳
                current = this.nextMs(lastTimestamp);
            }
        } else {
            //当前时间戳已经是下个毫秒
            sequence = 0L;
        }

        // 更新上次生成的ID时间戳
        lastTimestamp = current;

        // 进行移位操作生成int64的唯一ID
        return ((current - START_TIME) << TIMESTAMP_LEFT_SHIFT)
                | (this.appHostId << APP_HOST_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下个毫秒执行
     * @param timestamp 上个时间戳
     * @return 下个毫秒时间戳
     */
    private long nextMs(long timestamp) {
        long current = System.currentTimeMillis();
        while (current <= timestamp) {
            current = System.currentTimeMillis();
        }
        return current;
    }

    public static long generateId() {
        return UniqueIdGenerator.getInstance(IncrementIdGenerator.getServiceId()).nextId();
    }
}
