package xyz.vshmaliukh.tucantournament.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.model.GameStats;
import xyz.vshmaliukh.tucantournament.exceptions.ParseStatsFromFileException;
import xyz.vshmaliukh.tucantournament.convertors.RowsToPlayerMatchStatsUtil;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xyz.vshmaliukh.tucantournament.Constants.WINNER_ADDITIONAL_RATING_POINTS;

@Slf4j
@Component
public class StatsParser {

    public GameStats parseGameStats(File file) throws ParseStatsFromFileException {
        GameStats gameStats = new GameStats();

        try (FileReader reader = new FileReader(file);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'))) {

            String sportTypeStr = parseSportTypeStr(csvParser);
            List<List<String>> rows = parseRows(csvParser);

            List<PlayerMatchStats> playerMatchStatsList = RowsToPlayerMatchStatsUtil.convertRowsToPlayerMatchStatsList(sportTypeStr, rows);


            List<String> teamNameList = playerMatchStatsList.stream()
                    .map(PlayerMatchStats::getTeamName)
                    .toList();

            Map<String, Long> teamNamePointsMap = new HashMap<>();
            long pointsPerTeam;
            for (String teamName : teamNameList) {
                pointsPerTeam = playerMatchStatsList.stream()
                        .filter(playerMatchStats -> teamName.equalsIgnoreCase(playerMatchStats.getPlayerName()))
                        .mapToInt(PlayerMatchStats::calcCurrentMatchPlayerPoints)
                        .count();
                teamNamePointsMap.put(teamName, pointsPerTeam);
            }

            // Find the team with the highest score
            String teamWithHighestScore = teamNamePointsMap.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (teamWithHighestScore != null) {
                playerMatchStatsList.stream()
                        .filter(playerMatchStats -> teamWithHighestScore.equalsIgnoreCase(playerMatchStats.getTeamName()))
                        .forEach(playerMatchStats -> playerMatchStats.setWinner(true));
            } else {
                log.error("no winner team found");
            }

            playerMatchStatsList.forEach(playerMatchStats -> playerMatchStats.setCurrentMatchPoints(playerMatchStats.calcCurrentMatchPlayerPoints()));
            playerMatchStatsList.stream()
                    .filter(PlayerMatchStats::isWinner)
                    .forEach(playerMatchStats -> playerMatchStats.setCurrentMatchPoints(playerMatchStats.getCurrentMatchPoints() + WINNER_ADDITIONAL_RATING_POINTS));

            log.error("no winner team found: " + playerMatchStatsList);

            gameStats.setSportType(sportTypeStr);
            gameStats.setPlayerMatchStats(playerMatchStatsList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameStats;
    }

    private static List<List<String>> parseRows(CSVParser csvParser) {
        List<List<String>> rows = new ArrayList<>();
        for (CSVRecord csvRecord : csvParser) {
            rows.add(csvRecord.stream().toList());
        }
        return rows;
    }

    public static String parseValue(CSVRecord csvRecord, int index) {
        try {
            String value = csvRecord.get(index);
            log.debug("parsed value: '{}' // index: '{}'", value, index);
            return value;
        } catch (Exception e) {
            throw new ParseStatsFromFileException("problem to parse value by index", e);
        }
    }

    private static String parseSportTypeStr(CSVParser csvParser) {
        CSVRecord firstRecord = csvParser.iterator().next();
        return parseValue(firstRecord, 0).toLowerCase().trim();
    }

}