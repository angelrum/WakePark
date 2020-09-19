package ru.project.wakepark.to;

import ru.project.wakepark.util.ControlQueue;

import java.beans.ConstructorProperties;

public class QueueState {

    private ControlQueue state;

    private int time;

    private int endTime;

    private int queueTime;

    @ConstructorProperties({"state", "time", "queueTime"})
    public QueueState(ControlQueue state, int time, int queueTime, int endTime) {
        this.state = state;
        this.time = time;
        this.queueTime = queueTime;
        this.endTime = endTime;
    }

    public QueueState() {
    }

    public ControlQueue getState() {
        return state;
    }

    public int getTime() {
        return time;
    }

    public int getQueueTime() {
        return queueTime;
    }

    public void setState(ControlQueue state) {
        this.state = state;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setQueueTime(int queueTime) {
        this.queueTime = queueTime;
    }

    @Override
    public String toString() {
        return "QueueState{" +
                "state=" + state +
                ", time=" + time +
                ", queueTime=" + queueTime +
                '}';
    }
}
