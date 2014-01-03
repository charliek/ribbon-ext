package com.charlieknudsen.ribbon.etcd;

import com.charlieknudsen.etcd.EtcdClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EtcdPublisher {
    private static final Logger log = LoggerFactory.getLogger(EtcdPublisher.class);

    private final EtcdClient etcd;
    private final String host;
    private final int port;
    private final String appName;
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public EtcdPublisher(EtcdClient etcd, String host, int port, String appName) {
        this.etcd = etcd;
        this.host = host;
        this.port = port;
        this.appName = appName;
    }

    public static String buildKeyName(String appName) {
        return "/app/" + appName + "/servers/";
    }

    protected String getKeyName() {
        return buildKeyName(appName) + host + '-' + port;
    }

    protected String getKeyValue() {
        return host + ':' + port;
    }

    public void publishOnInterval(final long ttlSeconds, long publishSeconds) {
        final EtcdPublisher that = this;
        scheduledExecutorService.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try {
                            that.publish(ttlSeconds);
                        } catch (Exception e) {
                            log.error("Error publishing record to etcd", e);
                        }
                    }
                },
                0,
                publishSeconds,
                TimeUnit.SECONDS);
    }

    public void stop() {
        scheduledExecutorService.shutdown();
    }

    public void publish(long ttlSeconds) {
        final String keyName = getKeyName();
        final String keyValue = getKeyValue();
        log.debug("Publishing key '{}' with value {}", keyName, keyValue);
        etcd.setKeyTTL(keyName, keyValue, ttlSeconds);
    }
}
