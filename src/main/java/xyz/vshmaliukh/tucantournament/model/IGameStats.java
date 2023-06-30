package xyz.vshmaliukh.tucantournament.model;

import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

/**
 * Author: vshmaliukh
 * Represents the statistics of a game.
 * This interface defines the properties and methods to access game statistics.
 */
public interface IGameStats {

    /**
     * Gets the list of player match statistics.
     *
     * @return the list of player match statistics.
     */
    List<PlayerMatchStats> getPlayerMatchStats();

    /**
     * Gets the sport type of the game.
     *
     * @return the sport type of the game.
     */
    String getSportType();

    /**
     * Sets the sport type of the game.
     *
     * @param sportType the sport type to set.
     */
    void setSportType(String sportType);

    /**
     * Sets the list of player match statistics.
     *
     * @param playerMatchStats the list of player match statistics to set.
     */
    void setPlayerMatchStats(List<PlayerMatchStats> playerMatchStats);

}
