package com.charlieknudsen.ribbon.retrofit;

import com.google.common.collect.ImmutableList;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

import java.util.List;

public class StaticServerList implements ServerList<Server>, IClientConfigAware {

    private String clientName;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        clientName = clientConfig.getClientName();
    }

    @Override
    public List<Server> getInitialListOfServers() {
        if(clientName == null) {
            throw new IllegalStateException("Client name can not be null");
        }
        return ImmutableList.of(new Server("api.github.com", 443));
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        if(clientName == null) {
            throw new IllegalStateException("Client name can not be null");
        }
        return ImmutableList.of(new Server("api.github.com", 443));
    }
}
