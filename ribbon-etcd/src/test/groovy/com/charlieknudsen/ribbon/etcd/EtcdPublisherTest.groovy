package com.charlieknudsen.ribbon.etcd

import com.charlieknudsen.etcd.EtcdClient
import com.charlieknudsen.etcd.transfer.Action
import com.netflix.loadbalancer.Server
import spock.lang.Ignore
import spock.lang.Specification

class EtcdPublisherTest extends Specification {

    def 'test basic publish'() {
        given:
        EtcdClient client = new EtcdClient()
        String appName = "helloworld"
        String expectedDir = "/app/" + appName + "/servers"
        String expectedKey = "test.example.com-80"
        String expectedValue = "test.example.com:80"
        EtcdPublisher publisher = new EtcdPublisher(client, "test.example.com", 80, appName)
        EtcdServerList serverList = new EtcdServerList("http://127.0.0.1:4001", appName)

        when:
        publisher.publish(4)

        then:
        Action action = client.getKey("${expectedDir}/${expectedKey}")
        assert action.node.value == expectedValue
        assert action.node.ttl == 4
        assert !action.node.dir
        assert action.node.nodes == null
        List<Server> serviceList = serverList.initialListOfServers
        assert serviceList.size() == 1
        assert serviceList[0].id == expectedValue
    }

}
