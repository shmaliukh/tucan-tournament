package xyz.vshmaliukh.tucantournament.convertors.imp;

import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.convertors.AbstractRowToPlayerMatchStatsConvertor;
import xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.List;

@Component
public class BasketballRowToPlayerMatchStatsConvertor extends AbstractRowToPlayerMatchStatsConvertor {

    public BasketballRowToPlayerMatchStatsConvertor(SportActionsProvider sportActionsProvider) {
        super(sportActionsProvider);
    }

    @Override
    public PlayerMatchStats convertRowToPlayerMatchStats(String sportType, List<String> row) {
        PlayerMatchStats playerMatchStats = super.convertRowToPlayerMatchStats(sportType, row);

        convertValueToIntegerByIndex(sportType, sportActionsProvider.basketballScorePointIndex, playerMatchStats, row);
        convertValueToIntegerByIndex(sportType, sportActionsProvider.basketballReboundIndex, playerMatchStats, row);
        convertValueToIntegerByIndex(sportType, sportActionsProvider.basketballAssistIndex, playerMatchStats, row);

        return playerMatchStats;
    }

}