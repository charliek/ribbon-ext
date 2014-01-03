package com.charlieknudsen.etcd.transfer;

public class Action {
    String action;
    Node node;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "Action{" +
                "action='" + action + '\'' +
                ", node=" + node +
                '}';
    }
}
