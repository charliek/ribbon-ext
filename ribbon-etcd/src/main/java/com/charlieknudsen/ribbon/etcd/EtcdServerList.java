package com.charlieknudsen.ribbon.etcd;

import com.charlieknudsen.etcd.EtcdClient;
import com.charlieknudsen.etcd.transfer.Action;
import com.charlieknudsen.etcd.transfer.Node;
import com.netflix.client.IClientConfigAware;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit.RetrofitError;

import java.util.ArrayList;
import java.util.List;

public class EtcdServerList implements ServerList<Server>, IClientConfigAware {
    private static final Logger log = LoggerFactory.getLogger(EtcdServerList.class);
    private String keyPrefix;
    private EtcdClient etcd;

    public EtcdServerList() {
        // This constructor should only be when being created by ribbon framework. Initialization happens
        // in the initWithNiwsConfig function.
    }

    public EtcdServerList(String etcdHost, String appName) {
        etcd = new EtcdClient(etcdHost);
        this.keyPrefix = EtcdPublisher.buildKeyName(appName);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        String appName = clientConfig.getClientName();
        if (appName.endsWith("-client")) {
            appName = appName.substring(0, appName.length() - 7);
        }
        keyPrefix = clientConfig.getPropertyAsString(EtcdConfigKey.keyPrefix, EtcdPublisher.buildKeyName(appName));
        String etcdHost = clientConfig.getPropertyAsString(EtcdConfigKey.etcdHost, "http://127.0.0.1:4001");
        etcd = new EtcdClient(etcdHost);
    }

    private List<Server> getServerList() {
        if (keyPrefix == null) {
            throw new IllegalStateException("Client name can not be null");
        }
        log.debug("Looking up server list form etcd");
        List<Server> serverList = new ArrayList<>();
        Action action = null;
        try {
            action = etcd.getKey(keyPrefix);
        } catch (RetrofitError e) {
            log.error("Error looking up server list in etcd.", e);
            return serverList;
        }
        Node root = action.getNode();
        if (root.isDir()) {
            for (Node serverNode : root.getNodes()) {
                Server server = new Server(serverNode.getValue());
                serverList.add(server);
            }
        } else {
            log.error("Expected directory node and instead got {}", root);
        }
        log.debug("Server list returned {}", serverList);
        return serverList;
    }

    @Override
    public List<Server> getInitialListOfServers() {
        return getServerList();
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        return getServerList();
    }

}
