package xyz.vshmaliukh.tucantournament.parser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.vshmaliukh.tucantournament.convertors.imp.RowsToPlayerMatchStatsConvertor;
import xyz.vshmaliukh.tucantournament.handlers.StatsParser;

@SpringBootTest
class StatsParserTest {

    @Autowired
    RowsToPlayerMatchStatsConvertor rowsToPlayerMatchStatsConvertor;

    @Autowired
    StatsParser statsParser;

    @Test
    void parseGameStats() {
    }
}