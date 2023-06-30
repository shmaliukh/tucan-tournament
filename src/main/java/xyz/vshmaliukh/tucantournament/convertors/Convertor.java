package xyz.vshmaliukh.tucantournament.convertors;

import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

public interface Convertor {

    PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row);

}