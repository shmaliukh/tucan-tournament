package xyz.vshmaliukh.tucantournament.model;

import xyz.vshmaliukh.tucantournament.model.imp.Action;

/**
 * Author: vshmaliukh
 * Represents the statistics of a player in a match.
 * This interface extends the CalcPoints interface and defines the properties and methods to access player match statistics.
 */
public interface IPlayerMatchStats extends CalcPoints {

    void addAction(Action action, int counter);

    String getPlayerName();

    String getNickname();

    String getSportType();

    String getTeamName();

    int getNumber();

    int getCurrentMatchPoints();

    boolean isWinner();

    java.util.Map<Action, Integer> getActionCounterMap();

    void setPlayerName(String playerName);

    void setNickname(String nickname);

    void setSportType(String sportType);

    void setTeamName(String teamName);

    void setNumber(int number);

    void setCurrentMatchPoints(int currentMatchPoints);

    void setWinner(boolean isWinner);

    void setActionCounterMap(java.util.Map<Action, Integer> actionCounterMap);

}
