package xyz.vshmaliukh.tucantournament.handlers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
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

@Slf4j
@Component
public class StatsParser {

    RowsToPlayerMatchStatsConvertor rowsToPlayerMatchStatsConvertor;

    public StatsParser(RowsToPlayerMatchStatsConvertor rowsToPlayerMatchStatsConvertor) {
        this.rowsToPlayerMatchStatsConvertor = rowsToPlayerMatchStatsConvertor;
    }

    public IGameStats parseGameStats(File file) throws ParseStatsFromFileException {
        IGameStats IGameStats;
        try (FileReader reader = new FileReader(file);
             CSVParser csvParser = new CSVParser(reader, getCsvFormat())) {

            String sportTypeStr = parseSportTypeStr(csvParser);
            List<List<String>> rows = parseRows(csvParser);

            List<PlayerMatchStats> playerMatchStatsList = rowsToPlayerMatchStatsConvertor.convertRowsToPlayerMatchStatsList(sportTypeStr, rows);

            checkIsNoPlayerDuplicate(playerMatchStatsList);

            findWinnerTeam(playerMatchStatsList);

            playerMatchStatsList.forEach(PlayerMatchStats::calcCurrentMatchPlayerPoints);

            IGameStats = new GameStats(sportTypeStr, playerMatchStatsList);
        } catch (IOException ioe) {
            throw new ParseStatsFromFileException("problem to parse data from files", ioe);
        }
        return IGameStats;
    }

    private void checkIsNoPlayerDuplicate(List<PlayerMatchStats> playerMatchStatsList) throws ParseStatsFromFileException {
        long nickNameCounter = playerMatchStatsList.stream()
                .map(IPlayerMatchStats::getNickname)
                .distinct()
                .count();
        if(nickNameCounter != playerMatchStatsList.size()){
            throw new ParseStatsFromFileException("there is nickName duplicate in data");
        }
    }

    private CSVFormat getCsvFormat() {
        return CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .build();
    }

    private static void findWinnerTeam(List<PlayerMatchStats> playerMatchStatsList) {
        Map<String, Long> teamNamePointsMap = new HashMap<>();
        calcTeamNamePointsMap(playerMatchStatsList, teamNamePointsMap);
        // Find the team with the highest score
        solveWhoIsWinner(playerMatchStatsList, teamNamePointsMap);
    }

    private static List<String> readTeamNameList(List<PlayerMatchStats> playerMatchStatsList) {
        return playerMatchStatsList.stream()
                .map(PlayerMatchStats::getTeamName)
                .toList();
    }

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