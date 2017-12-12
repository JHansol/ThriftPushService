package com.naver.npns.server.model;

public class PushResult {
    private boolean pushResult;

    public boolean isPushResult() {
        return pushResult;
    }

    public void setPushResult(boolean pushResult) {
        this.pushResult = pushResult;
    }

    @Override
    public String toString() {
        return "PushResult{" +
                "pushResult=" + pushResult +
                '}';
    }
}
