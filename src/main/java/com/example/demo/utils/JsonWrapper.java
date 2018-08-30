package com.example.demo.utils;

import java.util.Date;

//TODO: Redo with ResponseEntity
public class JsonWrapper {

    private Date timeStamp;
    private STATUS status;
    private Object innerObject;

    public JsonWrapper(Object wrap, STATUS st) {
        this.innerObject = wrap;
        this.status = st;
        this.timeStamp = new Date();
    }

    public Object getInnerObject() {
        return innerObject;
    }

    public void setInnerObject(Object innerObject) {
        this.innerObject = innerObject;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public enum STATUS {OK, ERROR, NO_DATA}
}
