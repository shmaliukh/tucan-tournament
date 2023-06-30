package xyz.vshmaliukh.tucantournament.convertors;

import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

/**
 * Author: vshmaliukh
 * Interface for converting a row of data to PlayerMatchStats object.
 * Extends the Convertor interface.
 */
public interface IRowToPlayerMatchStatsConvertor extends Convertor {

    /**
     * Converts a value in the row to an integer based on the given index and updates the PlayerMatchStats object.
     *
     * @param sportType        the type of sport (basketball, handball, ...)
     * @param valueIndex       the index of the value in the row
     * @param playerMatchStats the PlayerMatchStats object to update
     * @param row              the row of data
     */
    void convertValueToIntegerByIndex(String sportType, int valueIndex, PlayerMatchStats playerMatchStats, List<String> row);

}
