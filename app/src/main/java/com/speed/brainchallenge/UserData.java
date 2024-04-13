package com.speed.brainchallenge;

public class UserData {
    private String username;
    private int score;
    private long time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public UserData(String username, int score, long time) {
        this.username = username;
        this.score = score;
        this.time = time;
    }


}
