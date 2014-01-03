package com.charlieknudsen.etcd

import com.charlieknudsen.etcd.transfer.Action

class ClientExample {

    public static void main(String[] args) throws Exception {
        EtcdApi etcd = new EtcdClient()
        Action a1 = etcd.setKey("/example/foo", "test")
        println a1
        Action a2 = etcd.getKey("/example")
        println a2
    }
}
