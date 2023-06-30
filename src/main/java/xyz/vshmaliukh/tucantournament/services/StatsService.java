package xyz.vshmaliukh.tucantournament.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.vshmaliukh.tucantournament.model.GameStats;
import xyz.vshmaliukh.tucantournament.exceptions.MultipartFileToFileException;
import xyz.vshmaliukh.tucantournament.exceptions.ParseStatsFromFileException;
import xyz.vshmaliukh.tucantournament.Utils;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;
import xyz.vshmaliukh.tucantournament.model.dto.MostValuablePlayer;
import xyz.vshmaliukh.tucantournament.parser.StatsParser;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatsService {

    final StatsParser statsParser;

    public StatsService(StatsParser statsParser) {
        this.statsParser = statsParser;
    }

    public Map<String, Long> analyzeFilesAndGetPlayerRatingMap(MultipartFile[] files) {
        Map<String, Long> playerRatingMap = new HashMap<>();
        try {
            Arrays.stream(files).forEach(multipartFile -> analyzeOneFile(playerRatingMap, multipartFile));
        } catch (ParseStatsFromFileException psffe) {
            log.error("problem to parse stats data // incorrect data structure", psffe);
        } catch (MultipartFileToFileException mftfe) {
            log.error("problem to convert MultipartFile to local File", mftfe);
        }
        return playerRatingMap;
    }

    private void analyzeOneFile(Map<String, Long> playerRatingMap, MultipartFile file) {
        File convertedFile = Utils.convertMultipartFileToFile(file);
        GameStats gameStats = statsParser.parseGameStats(convertedFile);
        persistNewGameStatsResult(playerRatingMap, gameStats);
    }

    public MostValuablePlayer findMostValuablePlayer(Map<String, Long> playerRatingMap) {
        String nickname = null;
        Long rating = Long.MIN_VALUE;

        for (Map.Entry<String, Long> entry : playerRatingMap.entrySet()) {
            if (entry.getValue() > rating) {
                rating = entry.getValue();
                nickname = entry.getKey();
            }
        }

        return new MostValuablePlayer(nickname, rating);
    }

    private static void persistNewGameStatsResult(Map<String, Long> playerRatingMap, GameStats gameStats) {
        Map<String, Long> playerNicknamePointesMap = collectNicknamePointsMap(gameStats);
        mergeNewGameStatsResultWithPrev(playerRatingMap, playerNicknamePointesMap);
    }

    private static Map<String, Long> collectNicknamePointsMap(GameStats gameStats) {
        return gameStats.getPlayerMatchStats().stream()
                .collect(Collectors.groupingBy(PlayerMatchStats::getNickname, Collectors.summingLong(PlayerMatchStats::getCurrentMatchPoints)));
    }

    private static void mergeNewGameStatsResultWithPrev(Map<String, Long> playerRatingMap, Map<String, Long> map) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            String name = entry.getKey();
            Long rating = entry.getValue();
            playerRatingMap.merge(name, rating, Long::sum);
        }
    }

}
