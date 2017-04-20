package com.demo.common.util.other;

import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.common.util.IpUtils;

/**
 * Snowflake
 */
@Component("idWorker")
public class IdWorker {

    private static final Logger log                = LoggerFactory.getLogger(IdWorker.class);

    private final long          twepoch            = 1288834974657L;
    private final long          workerIdBits       = 5L;
    private final long          datacenterIdBits   = 5L;
    private final long          maxWorkerId        = -1L ^ (-1L << workerIdBits);
    private final long          maxDatacenterId    = -1L ^ (-1L << datacenterIdBits);
    private final long          sequenceBits       = 12L;
    private final long          workerIdShift      = sequenceBits;
    private final long          datacenterIdShift  = sequenceBits + workerIdBits;
    private final long          timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long          sequenceMask       = -1L ^ (-1L << sequenceBits);

    private long                workerId;
    private long                datacenterId;
    private long                sequence           = 0L;
    private long                lastTimestamp      = -1L;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        try {
            String ip = IpUtils.getRealIp();
            if (StringUtils.isEmpty(ip)) {
                throw new RuntimeException("IdWorker get ip is empty");
            }
            this.workerId = this.datacenterId = Math.abs(ip.hashCode() % 31);
            log.info(
                String.format("ip:%s,workerId:%s,datacenterId；%s", ip, workerId, datacenterId));
        } catch (SocketException e) {
            log.error("init error,error:{}", e);
            throw new RuntimeException("IdWorker init error");
        }
    }

    /**
     * Instantiates a new Id worker.
     */
    public IdWorker() {
        super();
    }

    /**
     * Instantiates a new Id worker.
     *
     * @param workerId     the worker id
     * @param datacenterId the datacenter id
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String
                .format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * Next id long.
     *
     * @return the long
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
               | (workerId << workerIdShift) | sequence;
    }

    /**
     * Til next millis long.
     *
     * @param lastTimestamp the last timestamp
     * @return the long
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Time gen long.
     *
     * @return the long
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * test
     */
    static class IdWorkThread implements Runnable {
        private Set<Long> set;
        private IdWorker idWorker;

        /**
         * Instantiates a new Id work thread.
         *
         * @param set      the set
         * @param idWorker the id worker
         */
        public IdWorkThread(Set<Long> set, IdWorker idWorker) {
            this.set = set;
            this.idWorker = idWorker;
        }
 
        @Override
        public void run() {
            while (true) {
                long id = idWorker.nextId();
                System.out.println(Thread.currentThread().getName() + ":" + id);
                if (!set.add(id)) {
                    System.out.println("duplicate:" + id);
                }
            }
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Set<Long> set = new HashSet<Long>();
        final IdWorker idWorker1 = new IdWorker(0, 0);
        final IdWorker idWorker2 = new IdWorker(1, 0);
        Thread t1 = new Thread(new IdWorkThread(set, idWorker1));
        Thread t2 = new Thread(new IdWorkThread(set, idWorker2));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
