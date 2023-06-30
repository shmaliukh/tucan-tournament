package xyz.vshmaliukh.tucantournament.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.vshmaliukh.tucantournament.exceptions.MultipartFileToFileException;
import xyz.vshmaliukh.tucantournament.exceptions.ParseStatsFromFileException;
import xyz.vshmaliukh.tucantournament.Utils;
import xyz.vshmaliukh.tucantournament.model.IGameStats;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;
import xyz.vshmaliukh.tucantournament.model.dto.MostValuablePlayer;
import xyz.vshmaliukh.tucantournament.handlers.StatsParser;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Author: vshmaliukh
 * Class is a service component in the application that is responsible for analyzing
 * statistics data from multiple files, calculating player ratings, and finding
 * the most valuable player based on the ratings.
 */
@Slf4j
@Service
public class StatsService {

    public static final MostValuablePlayer EMPTY_DEFAULT_MOST_VALUABLE_PLAYER = new MostValuablePlayer(null, null);
    StatsParser statsParser;

    public StatsService(StatsParser statsParser) {
        this.statsParser = statsParser;
    }

    /**
     * Analyzes multiple files and calculates the rating for each player.
     *
     * @param files the array of files to analyze.
     * @return a map containing player nicknames as keys and their ratings as values.
     */
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


    /**
     * Finds the most valuable player based on the player ratings.
     *
     * @param playerRatingMap the map of player nicknames and their ratings.
     * @return the MostValuablePlayer object representing the most valuable player.
     */
    public MostValuablePlayer findMostValuablePlayer(Map<String, Long> playerRatingMap) {
        String nickname = null;
        Long rating = Long.MIN_VALUE;

        for (Map.Entry<String, Long> entry : playerRatingMap.entrySet()) {
            if (entry.getValue() > rating) {
                rating = entry.getValue();
                nickname = entry.getKey();
            }
        }

        return nickname != null ? new MostValuablePlayer(nickname, rating) : EMPTY_DEFAULT_MOST_VALUABLE_PLAYER;
    }

    private void analyzeOneFile(Map<String, Long> playerRatingMap, MultipartFile file) {
        File convertedFile = Utils.convertMultipartFileToFile(file);
        IGameStats gameStats = statsParser.parseGameStats(convertedFile);
        persistNewGameStatsResult(playerRatingMap, gameStats);
    }

    private static void persistNewGameStatsResult(Map<String, Long> playerRatingMap, IGameStats gameStats) {
        Map<String, Long> playerNicknamePointesMap = collectNicknamePointsMap(gameStats);
        mergeNewGameStatsResultWithPrev(playerRatingMap, playerNicknamePointesMap);
    }

    /**
     * Collects the nickname and points map from the game statistics.
     *
     * @param gameStats the game statistics.
     * @return a map with player nicknames as keys and their points as values.
     */
    private static Map<String, Long> collectNicknamePointsMap(IGameStats gameStats) {
        return gameStats.getPlayerMatchStats().stream()
                .collect(Collectors.toMap(PlayerMatchStats::getNickname, v -> (long) v.getCurrentMatchPoints()));
    }

    /**
     * Merges the new game stats result with the previous player rating map.
     * The method takes a player rating map and a map containing new game stats.
     * It merges the values from the new game stats map into the player rating map.
     *
     * @param playerRatingMap The map containing the player ratings.
     * @param map The map containing the new game stats.
     */
    private static void mergeNewGameStatsResultWithPrev(Map<String, Long> playerRatingMap, Map<String, Long> map) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            String name = entry.getKey();
            Long rating = entry.getValue();
            playerRatingMap.merge(name, rating, Long::sum);
        }
    }

}
