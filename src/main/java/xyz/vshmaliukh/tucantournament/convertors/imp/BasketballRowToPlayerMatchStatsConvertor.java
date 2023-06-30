package xyz.vshmaliukh.tucantournament.convertors.imp;

import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.convertors.AbstractRowToPlayerMatchStatsConvertor;
import xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

@Component
public class BasketballRowToPlayerMatchStatsConvertor extends AbstractRowToPlayerMatchStatsConvertor {

    protected BasketballRowToPlayerMatchStatsConvertor(SportActionsProvider sportActionsProvider, SportActionsProvider.SportDataConfig sportDataConfig) {
        super(sportActionsProvider, sportDataConfig);
    }

    @Override
    public PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row) {
        PlayerMatchStats playerMatchStats = super.convertRowToPlayerMatchStats(sportType, row);

        convertValueToIntegerByIndex(sportType, sportDataConfig.basketballScorePointIndex, playerMatchStats, row);
        convertValueToIntegerByIndex(sportType, sportDataConfig.basketballReboundIndex, playerMatchStats, row);
        convertValueToIntegerByIndex(sportType, sportDataConfig.basketballAssistIndex, playerMatchStats, row);

        return playerMatchStats;
    }

}