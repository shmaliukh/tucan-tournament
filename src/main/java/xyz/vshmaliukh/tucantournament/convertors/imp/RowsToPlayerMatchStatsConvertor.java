package xyz.vshmaliukh.tucantournament.convertors.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.convertors.Convertor;
import xyz.vshmaliukh.tucantournament.convertors.ConvertorProvider;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

/**
 * Author: vshmaliukh
 * Converts a list of rows to a list of PlayerMatchStats objects for different sports.
 */
@Slf4j
@Component
public class RowsToPlayerMatchStatsConvertor {

    ConvertorProvider convertorProvider;

    public RowsToPlayerMatchStatsConvertor(ConvertorProvider convertorProvider) {
        this.convertorProvider = convertorProvider;
    }

    /**
     * Converts a list of rows to a list of PlayerMatchStats objects for a given sport type.
     *
     * @param sportType the sport type String value
     * @param rows      the list of rows to convert
     * @return the list of PlayerMatchStats objects
     * @throws IllegalStateException if an unexpected convertor type is encountered
     */
    public List<PlayerMatchStats> convertRowsToPlayerMatchStatsList(String sportType, List<List<String>> rows) throws IllegalStateException {
        Convertor convertorByType = convertorProvider.getConvertorByType(sportType);
        // Convert each row to a PlayerMatchStats object using the convertor
        return rows.stream()
                .map(row -> convertorByType.convertRowToPlayerMatchStats(sportType, row))
                .toList();
    }

}

