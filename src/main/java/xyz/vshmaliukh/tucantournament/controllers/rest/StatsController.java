package xyz.vshmaliukh.tucantournament.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.vshmaliukh.tucantournament.model.dto.MostValuablePlayer;
import xyz.vshmaliukh.tucantournament.services.StatsService;

import java.util.Map;

@Slf4j
@RestController("/stats")
public class StatsController {

    StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/mvp")
    public MostValuablePlayer doPostFindMVP(@RequestParam("files") MultipartFile[] files) {
        if (files != null && files.length != 0) {
            Map<String, Long> playerRatingMap = statsService.analyzeFilesAndGetPlayerRatingMap(files);
            log.info("playerRatingMap: '{}'", playerRatingMap);
            return statsService.findMostValuablePlayer(playerRatingMap);
        } else {
            log.warn("no files about matches to find MostValuablePlayer");
        }
        log.error("not found MostValuablePlayer");
        return null;
    }

}
