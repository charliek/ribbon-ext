package com.charlieknudsen.ribbon.etcd;

import com.netflix.client.config.IClientConfigKey;

/**
 * Enum used to hold all config keys that the etcd module may need.
 */
public enum EtcdConfigKey implements IClientConfigKey {

    keyPrefix("KeyPrefix"),
    etcdHost("EtcdHost");

    private final String configKey;

    EtcdConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Override
    public String key() {
        return configKey;
    }
}
