package xyz.vshmaliukh.tucantournament.model;

import xyz.vshmaliukh.tucantournament.model.imp.Action;

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
