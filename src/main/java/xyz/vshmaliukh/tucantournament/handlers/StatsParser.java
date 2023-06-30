package xyz.vshmaliukh.tucantournament.handlers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.Constants;
import xyz.vshmaliukh.tucantournament.model.IPlayerMatchStats;
import xyz.vshmaliukh.tucantournament.model.imp.GameStats;
import xyz.vshmaliukh.tucantournament.exceptions.ParseStatsFromFileException;
import xyz.vshmaliukh.tucantournament.convertors.imp.RowsToPlayerMatchStatsConvertor;
import xyz.vshmaliukh.tucantournament.model.IGameStats;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: vshmaliukh
 * Class is responsible for parsing game statistics from a CSV file and converting them
 * into an IGameStats object. It utilizes various helper methods to parse the CSV file,
 * calculate team points, find the winner team, and perform other necessary operations.
 */

@Slf4j
@Component
public class StatsParser {

    RowsToPlayerMatchStatsConvertor rowsToPlayerMatchStatsConvertor;

    public StatsParser(RowsToPlayerMatchStatsConvertor rowsToPlayerMatchStatsConvertor) {
        this.rowsToPlayerMatchStatsConvertor = rowsToPlayerMatchStatsConvertor;
    }

    /**
     * Parses the game statistics from a CSV file and returns an IGameStats object.
     *
     * @param file the CSV file containing the game statistics
     * @return an IGameStats object representing the parsed game statistics
     * @throws ParseStatsFromFileException if there is an error parsing the statistics from the file
     */
    public IGameStats parseGameStats(File file) throws ParseStatsFromFileException {
        IGameStats gameStats;
        try (FileReader reader = new FileReader(file);
             CSVParser csvParser = new CSVParser(reader, getCsvFormat())) {

            String sportTypeStr = parseSportTypeStr(csvParser);
            List<List<String>> rows = parseRows(csvParser);

            // Convert the rows to a list of PlayerMatchStats objects
            List<PlayerMatchStats> playerMatchStatsList = rowsToPlayerMatchStatsConvertor.convertRowsToPlayerMatchStatsList(sportTypeStr, rows);

            checkIsNoPlayerDuplicate(playerMatchStatsList);
            findWinnerTeam(playerMatchStatsList);
            playerMatchStatsList.forEach(PlayerMatchStats::calcCurrentMatchPlayerPoints);

            // Create the GameStats object with the sport type and player match stats
            gameStats = new GameStats(sportTypeStr, playerMatchStatsList);
        } catch (IOException ioe) {
            throw new ParseStatsFromFileException("problem to parse data from files", ioe);
        }
        return gameStats;
    }

    /**
     * Checks if there are any duplicate player nicknames in the list of player match stats.
     *
     * @param playerMatchStatsList the list of player match stats
     * @throws ParseStatsFromFileException if a duplicate nickname is found
     */
    private void checkIsNoPlayerDuplicate(List<PlayerMatchStats> playerMatchStatsList) throws ParseStatsFromFileException {
        long nickNameCounter = playerMatchStatsList.stream()
                .map(IPlayerMatchStats::getNickname)
                .distinct()
                .count();
        if (nickNameCounter != playerMatchStatsList.size()) {
            throw new ParseStatsFromFileException("there is nickName duplicate in data");
        }
    }

    /**
     * Returns the CSV format used for parsing the CSV file.
     *
     * @return the CSVFormat object
     */
    private CSVFormat getCsvFormat() {
        return CSVFormat.DEFAULT.builder()
                .setDelimiter(Constants.DELIMITER_SYMBOL)
                .build();
    }

    /**
     * Finds the winner team based on the points scored by each team.
     *
     * @param playerMatchStatsList the list of player match stats
     */
    private static void findWinnerTeam(List<PlayerMatchStats> playerMatchStatsList) {
        Map<String, Long> teamNamePointsMap = new HashMap<>();
        calcTeamNamePointsMap(playerMatchStatsList, teamNamePointsMap);
        // Find the team with the highest score
        solveWhoIsWinner(playerMatchStatsList, teamNamePointsMap);
    }

    /**
     * Reads the team names from the list of player match stats.
     *
     * @param playerMatchStatsList the list of player match stats
     * @return a list of team names
     */
    private static List<String> readTeamNameList(List<PlayerMatchStats> playerMatchStatsList) {
        return playerMatchStatsList.stream()
                .map(PlayerMatchStats::getTeamName)
                .toList();
    }

    /**
     * Calculates the points for each team and stores them in the teamNamePointsMap.
     *
     * @param playerMatchStatsList the list of player match stats
     * @param teamNamePointsMap    the map to store the team names and their points
     */
    private static void calcTeamNamePointsMap(List<PlayerMatchStats> playerMatchStatsList, Map<String, Long> teamNamePointsMap) {
        long pointsPerTeam;
        List<String> teamNameList = readTeamNameList(playerMatchStatsList);
        for (String teamName : teamNameList) {
            pointsPerTeam = playerMatchStatsList.stream()
                    .filter(playerMatchStats -> teamName.equalsIgnoreCase(playerMatchStats.getTeamName()))
                    .mapToInt(PlayerMatchStats::calcCurrentMatchPlayerPoints)
                    .sum();
            teamNamePointsMap.put(teamName, pointsPerTeam);
        }
    }

    /**
     * Determines the winner team based on the points scored by each team.
     *
     * @param playerMatchStatsList the list of player match stats
     * @param teamNamePointsMap    the map containing the team names and their points
     */
    private static void solveWhoIsWinner(List<PlayerMatchStats> playerMatchStatsList, Map<String, Long> teamNamePointsMap) {
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
    }

    /**
     * Parses the rows from the CSVParser and returns them as a list of lists (like a matrix).
     *
     * @param csvParser the CSVParser object
     * @return a list of rows (each row represented as a list of strings)
     */
    private static List<List<String>> parseRows(CSVParser csvParser) {
        List<List<String>> rows = new ArrayList<>();
        for (CSVRecord csvRecord : csvParser) {
            rows.add(csvRecord.stream().toList());
        }
        return rows;
    }

    /**
     * Parses a value from a CSVRecord based on the given index.
     *
     * @param csvRecord the CSVRecord object
     * @param index     the index of the value to parse
     * @return the parsed value as a string
     * @throws ParseStatsFromFileException if there is an error parsing the value
     */
    public static String parseValue(CSVRecord csvRecord, int index) {
        try {
            String value = csvRecord.get(index);
            log.debug("parsed value: '{}' // index: '{}'", value, index);
            return value;
        } catch (Exception e) {
            throw new ParseStatsFromFileException("problem to parse value by index", e);
        }
    }

    /**
     * Parses the sport type string from the CSVParser.
     *
     * @param csvParser the CSVParser object
     * @return the parsed sport type string
     */
    private static String parseSportTypeStr(CSVParser csvParser) {
        CSVRecord firstRecord = csvParser.iterator().next();
        return parseValue(firstRecord, 0).toLowerCase().trim();
    }

}