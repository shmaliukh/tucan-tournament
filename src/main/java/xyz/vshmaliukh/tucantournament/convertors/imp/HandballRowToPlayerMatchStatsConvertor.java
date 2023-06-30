package xyz.vshmaliukh.tucantournament.convertors.imp;

import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.convertors.AbstractRowToPlayerMatchStatsConvertor;
import xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

@Component
public class HandballRowToPlayerMatchStatsConvertor extends AbstractRowToPlayerMatchStatsConvertor {

    protected HandballRowToPlayerMatchStatsConvertor(SportActionsProvider sportActionsProvider, SportActionsProvider.SportDataConfig sportDataConfig) {
        super(sportActionsProvider, sportDataConfig);
    }

    @Override
    public PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row) {
        PlayerMatchStats playerMatchStats = super.convertRowToPlayerMatchStats(sportType, row);

        convertValueToIntegerByIndex(sportType, sportDataConfig.handballGoalMadeIndex, playerMatchStats, row);
        convertValueToIntegerByIndex(sportType, sportDataConfig.handballGoalReceivedIndex, playerMatchStats, row);

        return playerMatchStats;
    }

}