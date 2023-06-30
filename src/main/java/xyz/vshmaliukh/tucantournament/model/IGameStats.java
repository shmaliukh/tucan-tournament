package xyz.vshmaliukh.tucantournament.model;

import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

public interface IGameStats {
    List<PlayerMatchStats> getPlayerMatchStats();

    String getSportType();

    void setSportType(String sportType);

    void setPlayerMatchStats(List<PlayerMatchStats> playerMatchStats);
}
