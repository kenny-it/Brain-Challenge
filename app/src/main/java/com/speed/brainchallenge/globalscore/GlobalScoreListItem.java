package com.speed.brainchallenge.globalscore;

public class GlobalScoreListItem {
    private String username;
    private int rank;
    private int score;
    private String time;

    public GlobalScoreListItem(String username, int rank, int score, String time) {
        this.username = username;
        this.rank = rank;
        this.score = score;
        this.time = time;
    }

    public GlobalScoreListItem(String username, int score, String time) {
        this.username = username;
        this.score = score;
        this.time = time;
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
