package xyz.vshmaliukh.tucantournament.model.imp;

import xyz.vshmaliukh.tucantournament.model.AbstractPlayerMatchStats;

import static xyz.vshmaliukh.tucantournament.Constants.WINNER_ADDITIONAL_RATING_POINTS;

public class PlayerMatchStats extends AbstractPlayerMatchStats {

    @Override
    public int calcCurrentMatchPlayerPoints() {
        currentMatchPoints = super.calcCurrentMatchPlayerPoints();
        if (isWinner) {
            currentMatchPoints += WINNER_ADDITIONAL_RATING_POINTS;
        }
        return currentMatchPoints;
    }

}
