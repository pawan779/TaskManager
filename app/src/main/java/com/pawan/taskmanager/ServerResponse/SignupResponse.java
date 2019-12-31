package com.pawan.taskmanager.ServerResponse;

public class SignupResponse {
    private String status;
    private String toke;

    public SignupResponse(String status, String toke) {
        this.status = status;
        this.toke = toke;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToke() {
        return toke;
    }

    public void setToke(String toke) {
        this.toke = toke;
    }
}
