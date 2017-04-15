package com.example.prateek.bluekey;

/**
 * Created by prateek on 16/2/17.
 */

public class Request {

    private String userName, profileImage, timestamp, RMN;

    public Request(){}

    public Request(String userName, String profileImage, String timestamp, String RMN) {
        this.userName = userName;
        this.profileImage = profileImage;
        this.timestamp = timestamp;
        this.RMN = RMN;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRMN() {
        return RMN;
    }

    public void setRMN(String RMN) {
        this.RMN = RMN;
    }
}
