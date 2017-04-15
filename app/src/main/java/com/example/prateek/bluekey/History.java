package com.example.prateek.bluekey;

import java.sql.Timestamp;

/**
 * Created by prateek on 12/2/17.
 */

public class History {

    private String profileImageUrl, userName, action, lockname;
    private String timestamp;

    public History(){

    }

    public String getLockname() {
        return lockname;
    }

    public void setLockname(String lockname) {
        this.lockname = lockname;
    }

    public History(String profileImageUrl, String userName, String timeStamp, String action, String lockname) {
        this.profileImageUrl = profileImageUrl;
        this.userName = userName;
        this.action = action;
        this.timestamp = timeStamp;
        this.lockname = lockname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String actionImage) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
