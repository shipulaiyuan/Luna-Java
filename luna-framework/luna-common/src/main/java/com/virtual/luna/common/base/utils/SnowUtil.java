package com.virtual.luna.common.base.utils;

/**
 * 雪花算法生成分布式id
 */
public class SnowUtil {

    private long sequence = 0L;// 序列号
    private long machineId = 1L;// 自定义 节点
    private static long lastTimestamp = -1L;// 上次时间
    private final static long START_TIME_STAMP = 1717553240000L;// 开始时间戳 2024-06-05 10:07:20

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 11L;
    private final static long MACHINE_BIT = 10L;

    /**
     * 每一部分的最大值
     */
    // 1024/节点
    private static long MACHINE_MAX = -1L ^ (-1L << MACHINE_BIT);
    // 2048/序号
    private final static long SEQUENCE_MAX = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    // 11位
    private final static long MACHINE_ID_LEFT = SEQUENCE_BIT;
    // 22位
    private final static long TIME_STAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    private static SnowUtil snowUtil = null;

    static {
        snowUtil = new SnowUtil();
    }

    /**
     * 生成唯一id
     * @return
     */
    public static synchronized long nextId() {
        return snowUtil.getNextId();
    }


    private SnowUtil() {
        this.machineId = machineId & MACHINE_MAX;
    }

    public synchronized long getNextId() {
        long timestamp = getCurrentTimeStamp();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MAX;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long nextId = ((timestamp - START_TIME_STAMP) << TIME_STAMP_LEFT)  | (machineId << MACHINE_ID_LEFT) | sequence;
        return nextId;
    }

    /**
     * 再次获取时间戳直到获取的时间戳与现有的不同
     *
     * @param lastTimestamp
     * @return 下一个时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.getCurrentTimeStamp();
        while (timestamp <= lastTimestamp) {
            timestamp = this.getCurrentTimeStamp();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     */
    protected long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

}

