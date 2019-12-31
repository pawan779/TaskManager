package com.pawan.taskmanager.ServerResponse;

public class ImageResponse {

    private String fileName;

    public ImageResponse(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
