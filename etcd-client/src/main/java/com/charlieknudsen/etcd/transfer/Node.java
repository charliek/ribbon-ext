package com.charlieknudsen.etcd.transfer;

import java.util.List;

public class Node {

    int createdIndex;
    String expiration; // 2013-12-04T12:01:21.874888581-08:00
    boolean dir = false;
    String key;
    int modifiedIndex;
    int ttl = -1;
    String value;
    List<Node> nodes;

    public int getCreatedIndex() {
        return createdIndex;
    }

    public void setCreatedIndex(int createdIndex) {
        this.createdIndex = createdIndex;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public boolean isDir() {
        return dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getModifiedIndex() {
        return modifiedIndex;
    }

    public void setModifiedIndex(int modifiedIndex) {
        this.modifiedIndex = modifiedIndex;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Node{" +
                "createdIndex=" + createdIndex +
                ", expiration='" + expiration + '\'' +
                ", dir=" + dir +
                ", key='" + key + '\'' +
                ", modifiedIndex=" + modifiedIndex +
                ", ttl=" + ttl +
                ", value='" + value + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
