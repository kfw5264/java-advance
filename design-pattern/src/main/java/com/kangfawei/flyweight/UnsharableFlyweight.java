package com.kangfawei.flyweight;

public class UnsharableFlyweight {
    private String info;
    public UnsharableFlyweight(String info){
        this.info = info;
    }

    public String getInfo(){
        return info;
    }

    public void setInfo(){
        this.info = info;
    }
}
