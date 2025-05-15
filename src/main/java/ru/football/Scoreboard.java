package ru.football;

import ru.football.exceptions.DuplicateMatchException;
import ru.football.exceptions.InvalidScoreException;
import ru.football.exceptions.MatchNotFoundException;
import ru.football.model.Match;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scoreboard {
    private final Map<String, Match> matches = new HashMap<>();

    public void startGame(String homeTeam, String awayTeam) {
        String key = generateMatchKey(homeTeam, awayTeam);

        if (matches.containsKey(key)) {
            throw new DuplicateMatchException("Match already exists: " + homeTeam + " vs " + awayTeam);
        }

        Match match = new Match(homeTeam, awayTeam);
        matches.put(key, match);
    }

    public void finishGame(String homeTeam, String awayTeam) {
        String key = generateMatchKey(homeTeam, awayTeam);

        if (!matches.containsKey(key)) {
            throw new MatchNotFoundException("Match not found: " + homeTeam + " vs " + awayTeam);
        }

        matches.remove(key);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new InvalidScoreException("Scores cannot be negative");
        }

        String key = generateMatchKey(homeTeam, awayTeam);
        Match match = matches.get(key);

        if (match == null) {
            throw new MatchNotFoundException("Match not found: " + homeTeam + " vs " + awayTeam);
        }

        match.updateScore(homeScore, awayScore);
    }

    public List<Match> getSummary() {
        return matches.values().stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore)
                        .reversed()
                        .thenComparing(Match::getStartTime).reversed())
                .collect(Collectors.toList());
    }

    private String generateMatchKey(String homeTeam, String awayTeam) {
        return homeTeam.toLowerCase() + ":" + awayTeam.toLowerCase();
    }

    public int getMatchesCount() {
        return matches.size();
    }
}