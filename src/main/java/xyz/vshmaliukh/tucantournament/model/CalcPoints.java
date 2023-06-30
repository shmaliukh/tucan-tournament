package xyz.vshmaliukh.tucantournament.model;

/**
 * Author: vshmaliukh
 * Represents the calculation of player points.
 * This interface defines the method to calculate the player rating.
 */
public interface CalcPoints {

    /**
     * Calculates the player rating.
     *
     * @return the calculated player rating.
     */
    int calcCurrentMatchPlayerPoints();

}
