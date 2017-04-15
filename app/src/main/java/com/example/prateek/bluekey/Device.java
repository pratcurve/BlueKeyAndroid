package com.example.prateek.bluekey;

/**
 * Created by prateek on 23/2/17.
 */

public class Device {

    private String lockName;
    private String lockAddress;
    private String device_id;
    private String data_device_id;

    public Device(){}

    public Device (String lockName, String lockAddress){
        this.lockName = lockName;
        this.lockAddress = lockAddress;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getLockAddress() {
        return lockAddress;
    }

    public void setLockAddress(String lockAddress) {
        this.lockAddress = lockAddress;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getData_device_id() {
        return data_device_id;
    }

    public void setData_device_id(String data_device_id) {
        this.data_device_id = data_device_id;
    }
}
