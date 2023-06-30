package xyz.vshmaliukh.tucantournament.convertors;

import xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider;
import xyz.vshmaliukh.tucantournament.model.imp.Action;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;
import java.util.Optional;

/**
 * Author: vshmaliukh
 * Abstract class implementing the IRowToPlayerMatchStatsConvertor interface.
 * Provides common functionality for converting a row of data to PlayerMatchStats object.
 */
public abstract class AbstractRowToPlayerMatchStatsConvertor implements IRowToPlayerMatchStatsConvertor {

    protected SportActionsProvider sportActionsProvider;
    protected final SportActionsProvider.SportDataConfig sportDataConfig;

    protected AbstractRowToPlayerMatchStatsConvertor(SportActionsProvider sportActionsProvider,
                                                     SportActionsProvider.SportDataConfig sportDataConfig) {
        this.sportActionsProvider = sportActionsProvider;
        this.sportDataConfig = sportDataConfig;
    }

    /**
     * Constructor for AbstractRowToPlayerMatchStatsConvertor.
     *
     * @param sportActionsProvider the SportActionsProvider to retrieve sport actions
     * @param sportDataConfig      the configuration for sport data indexes
     * @return the converted PlayerMatchStats object
     */
    public PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row) {
        PlayerMatchStats playerMatchStats = new PlayerMatchStats();
        playerMatchStats.setSportType(sportType);
        playerMatchStats.setPlayerName(row.get(sportDataConfig.playerMatchStatsNameIndex));
        playerMatchStats.setNickname(row.get(sportDataConfig.playerMatchStatsNickIndex));
        playerMatchStats.setNumber(Integer.parseInt(row.get(sportDataConfig.playerMatchStatsNumberIndex)));
        playerMatchStats.setTeamName(row.get(sportDataConfig.playerMatchStatsTeamNameIndex));
        return playerMatchStats;
    }

    @Override
    public void convertValueToIntegerByIndex(String sportType, int valueIndex, PlayerMatchStats playerMatchStats, List<String> row) {
        Optional<Action> optionalScorePointAction = sportActionsProvider.getActionBySportAndPosition(sportType, valueIndex);
        optionalScorePointAction.ifPresent(action -> playerMatchStats.addAction(action, Integer.parseInt(row.get(valueIndex))));
    }

}
