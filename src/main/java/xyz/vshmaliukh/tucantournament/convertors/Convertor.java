package xyz.vshmaliukh.tucantournament.convertors;

import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

/**
 * Author: vshmaliukh
 * Convertor interface defines a contract for converting a row of data into PlayerMatchStats object.
 */
public interface Convertor {

    /**
     * Converts a row of data into a PlayerMatchStats object based on the sport type str value.
     *
     * @param sportType the type of sport (basketball, handball, ...)
     * @param row       the row of data representing the player's match stats
     * @return the converted PlayerMatchStats object
     */
    PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row);

}