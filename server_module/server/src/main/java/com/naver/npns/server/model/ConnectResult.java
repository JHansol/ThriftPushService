package com.naver.npns.server.model;

public class ConnectResult {
    private String connectResult;

    public String getConnectResult() {
        return connectResult;
    }

    public void setConnectResult(String connectResult) {
        this.connectResult = connectResult;
    }

    @Override
    public String toString() {
        return "ConnectResult{" +
                "connectResult='" + connectResult + '\'' +
                '}';
    }
}
