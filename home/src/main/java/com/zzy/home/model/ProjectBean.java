package com.zzy.home.model;

/**
 * @author zzy
 * @date 2018/5/19
 */

public class ProjectBean {
    private String name;
    private int type;
    private String rate;
    private String duration;
    private int state;
    private String desc;
    private String startMoney;
    private String startTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(String startMoney) {
        this.startMoney = startMoney;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
