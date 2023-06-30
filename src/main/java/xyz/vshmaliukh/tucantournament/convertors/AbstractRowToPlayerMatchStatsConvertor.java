package xyz.vshmaliukh.tucantournament.convertors;

import xyz.vshmaliukh.tucantournament.Constants;
import xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider;
import xyz.vshmaliukh.tucantournament.model.Action;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;
import java.util.Optional;

public abstract class AbstractRowToPlayerMatchStatsConvertor implements Convertor {

    protected SportActionsProvider sportActionsProvider;

    protected AbstractRowToPlayerMatchStatsConvertor(SportActionsProvider sportActionsProvider) {
        this.sportActionsProvider = sportActionsProvider;
    }

    public PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row) {
        PlayerMatchStats playerMatchStats = new PlayerMatchStats();
        playerMatchStats.setSportType(sportType);
        playerMatchStats.setPlayerName(row.get(Constants.PLAYER_MATCH_STATS_NAME_INDEX));
        playerMatchStats.setNickname(row.get(Constants.PLAYER_MATCH_STATS_NICK_INDEX));
        playerMatchStats.setNumber(Integer.parseInt(row.get(Constants.PLAYER_MATCH_STATS_NUMBER_INDEX)));
        playerMatchStats.setTeamName(row.get(Constants.PLAYER_MATCH_STATS_TEAM_NAME_INDEX));
        return playerMatchStats;
    }

    protected void convertValueToIntegerByIndex(String sportType, int valueIndex, PlayerMatchStats playerMatchStats, List<String> row) {
        Optional<Action> optionalScorePointAction = sportActionsProvider.getActionBySportAndPosition(sportType, valueIndex);
        optionalScorePointAction.ifPresent(action -> playerMatchStats.addAction(action, Integer.parseInt(row.get(valueIndex))));
    }

}
