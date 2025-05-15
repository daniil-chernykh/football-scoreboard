package ru.football.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, 0, 0);
    }

    public Match(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeTeam == null || awayTeam == null || homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team names cannot be null or empty");
        }
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.startTime = LocalDateTime.now();
    }

    public String getHomeTeam() { return homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public int getHomeScore() { return homeScore; }
    public int getAwayScore() { return awayScore; }
    public LocalDateTime getStartTime() { return startTime; }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Match match)) return false;
        return getHomeScore() == match.getHomeScore() && getAwayScore() == match.getAwayScore() && Objects.equals(getHomeTeam(), match.getHomeTeam()) && Objects.equals(getAwayTeam(), match.getAwayTeam()) && Objects.equals(getStartTime(), match.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHomeTeam(), getAwayTeam(), getHomeScore(), getAwayScore(), getStartTime());
    }
}
