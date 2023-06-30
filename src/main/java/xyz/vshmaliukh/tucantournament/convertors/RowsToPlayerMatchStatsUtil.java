package xyz.vshmaliukh.tucantournament.convertors;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

@Slf4j
@UtilityClass
public class RowsToPlayerMatchStatsUtil {

    public static List<PlayerMatchStats> convertRowsToPlayerMatchStatsList(String sportType, List<List<String>> rows) throws IllegalStateException {
        Convertor convertorByType = ConvertorProvider.getConvertorByType(sportType);
        return rows.stream()
                .map(row -> convertorByType.convertRowToPlayerMatchStats(sportType, row))
                .toList();
    }

}

