package com.example.prateek.bluekey;

/**
 * Created by prateek on 22/2/17.
 */

public class Home {

    private String lockName;
    private String lockStatus;
    private String lockAddress;

    public Home() {}

    public Home(String lockName,String lockAddress, String lockStatus) {
        this.lockName = lockName;
        this.lockStatus = lockStatus;
        this.lockAddress = lockAddress;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getLockAddress() {
        return lockAddress;
    }

    public void setLockAddress(String lockAddress) {
        this.lockAddress = lockAddress;
    }
}
