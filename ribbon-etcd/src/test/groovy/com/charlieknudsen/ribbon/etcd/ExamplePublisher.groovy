package com.charlieknudsen.ribbon.etcd

import com.charlieknudsen.etcd.EtcdClient

class ExamplePublisher {

    public static void main(String [] args) {
        String etcdHost = "http://127.0.0.1:4001"
        String appName = "appName"
        EtcdClient client = new EtcdClient(etcdHost)
        EtcdPublisher publisher = new EtcdPublisher(client, "host", 8080, appName)
        EtcdServerList serverList = new EtcdServerList(etcdHost, appName)
        publisher.publishOnInterval(10, 3)
        (1..3).each {
            Thread.sleep(5 * 1000)
            println serverList.updatedListOfServers
        }
        publisher.stop()
    }

}
