package xyz.vshmaliukh.tucantournament.model.imp;

import xyz.vshmaliukh.tucantournament.model.AbstractPlayerMatchStats;

import static xyz.vshmaliukh.tucantournament.Constants.WINNER_ADDITIONAL_RATING_POINTS;

/**
 * Author: vshmaliukh
 * Represents the match statistics for a player.
 * Extends the AbstractPlayerMatchStats class.
 */
public class PlayerMatchStats extends AbstractPlayerMatchStats {

    /**
     * Calculates the current match player points.
     * Overrides the method from the superclass.
     * If the player is marked as a winner (isWinner is true),
     * it adds the additional rating points
     *
     * @return the calculated current match player points
     */
    @Override
    public int calcCurrentMatchPlayerPoints() {
        currentMatchPoints = super.calcCurrentMatchPlayerPoints();
        if (isWinner) {
            currentMatchPoints += WINNER_ADDITIONAL_RATING_POINTS;
        }
        return currentMatchPoints;
    }

}
