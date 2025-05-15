# Football World Cup Scoreboard

Простая Java-библиотека для отображения и управления результатами матчей Чемпионата мира по футболу.

## Требования

- Java 8 или выше
- Maven (для сборки)

## Установка

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/daniil-chernykh/football-scoreboard.git
   cd football-scoreboard
   ```
2. Сборка проекта:
    ```bash
   mvn clean install
   mvn clean test
   ```

## Пример использования 
Добавьте зависимость в ваш проект (Maven):
```xml
   <dependency>
       <groupId>com.football</groupId>
       <artifactId>football_scoreboard</artifactId>
       <version>1.0.0</version>
   </dependency>
```

Пример применения 
```java
import com.football.Scoreboard;
import com.football.model.Match;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
        
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.startGame("Spain", "Brazil");
        scoreboard.startGame("Germany", "France");
        
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);
        
        System.out.println("Current matches summary:");
        for (Match match : scoreboard.getSummary()) {
            System.out.println(match);
        }
        
        scoreboard.finishGame("Germany", "France");
    }
}
```
