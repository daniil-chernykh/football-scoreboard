package ru.footbal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.football.Scoreboard;
import ru.football.exceptions.DuplicateMatchException;
import ru.football.exceptions.InvalidScoreException;
import ru.football.exceptions.MatchNotFoundException;
import ru.football.model.Match;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreboardTest {
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void startGame_shouldAddNewMatch() {
        scoreboard.startGame("Mexico", "Canada");
        assertEquals(1, scoreboard.getMatchesCount());
    }

    @Test
    void startGame_shouldThrowWhenDuplicate() {
        scoreboard.startGame("Mexico", "Canada");
        assertThrows(DuplicateMatchException.class, () ->
                scoreboard.startGame("Mexico", "Canada"));
    }

    @Test
    void finishGame_shouldRemoveMatch() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.finishGame("Mexico", "Canada");
        assertEquals(0, scoreboard.getMatchesCount());
    }

    @Test
    void finishGame_shouldThrowWhenNotFound() {
        assertThrows(MatchNotFoundException.class, () ->
                scoreboard.finishGame("Mexico", "Canada"));
    }

    @Test
    void updateScore_shouldUpdateMatchScore() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        List<Match> summary = scoreboard.getSummary();
        assertEquals("Mexico 0 - Canada 5", summary.get(0).toString());
    }

    @Test
    void updateScore_shouldThrowWhenInvalidScore() {
        scoreboard.startGame("Mexico", "Canada");
        assertThrows(InvalidScoreException.class, () ->
                scoreboard.updateScore("Mexico", "Canada", -1, 0));
    }

    @Test
    void getSummary_shouldOrderByTotalScoreDesc() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startGame("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startGame("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Spain 10 - Brazil 2", summary.get(0).toString());
        assertEquals("Mexico 0 - Canada 5", summary.get(1).toString());
        assertEquals("Germany 2 - France 2", summary.get(2).toString());
    }

    @Test
    void getSummary_shouldOrderByStartTimeWhenScoresEqual() throws InterruptedException {
        scoreboard.startGame("Uruguay", "Italy");
        Thread.sleep(10); // Для гарантированного различия во времени
        scoreboard.startGame("Argentina", "Australia");

        // Устанавливаем одинаковый счет
        scoreboard.updateScore("Uruguay", "Italy", 3, 1);
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        // Более поздний матч должен быть первым при равных счетах
        assertEquals("Argentina 3 - Australia 1", summary.get(0).toString());
        assertEquals("Uruguay 3 - Italy 1", summary.get(1).toString());
    }
}
