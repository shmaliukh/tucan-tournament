package xyz.vshmaliukh.tucantournament.convertors.imp;

import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.convertors.AbstractRowToPlayerMatchStatsConvertor;
import xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

@Component
public class HandballRowToPlayerMatchStatsConvertor extends AbstractRowToPlayerMatchStatsConvertor {

    public HandballRowToPlayerMatchStatsConvertor(SportActionsProvider sportActionsProvider) {
        super(sportActionsProvider);
    }

    @Override
    public PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row) {
        PlayerMatchStats playerMatchStats = super.convertRowToPlayerMatchStats(sportType, row);

        convertValueToIntegerByIndex(sportType, sportActionsProvider.handballGoalMadeIndex, playerMatchStats, row);
        convertValueToIntegerByIndex(sportType, sportActionsProvider.handballGoalReceivedIndex, playerMatchStats, row);

        return playerMatchStats;
    }

}