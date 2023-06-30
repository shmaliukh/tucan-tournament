package xyz.vshmaliukh.tucantournament.convertors;

import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.convertors.imp.BasketballRowToPlayerMatchStatsConvertor;
import xyz.vshmaliukh.tucantournament.convertors.imp.HandballRowToPlayerMatchStatsConvertor;

import static xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider.SPORT_BASKETBALL_STR;
import static xyz.vshmaliukh.tucantournament.handlers.SportActionsProvider.SPORT_HANDBALL_STR;

@Component
public class ConvertorProvider {

    BasketballRowToPlayerMatchStatsConvertor basketballRowToPlayerMatchStatsConvertor;
    HandballRowToPlayerMatchStatsConvertor handballRowToPlayerMatchStatsConvertor;

    public ConvertorProvider(BasketballRowToPlayerMatchStatsConvertor basketballRowToPlayerMatchStatsConvertor,
                             HandballRowToPlayerMatchStatsConvertor handballRowToPlayerMatchStatsConvertor) {
        this.basketballRowToPlayerMatchStatsConvertor = basketballRowToPlayerMatchStatsConvertor;
        this.handballRowToPlayerMatchStatsConvertor = handballRowToPlayerMatchStatsConvertor;
    }

    public Convertor getConvertorByType(String convertorType) {
        switch (convertorType) {
            case SPORT_BASKETBALL_STR -> {
                return basketballRowToPlayerMatchStatsConvertor;
            }
            case SPORT_HANDBALL_STR -> {
                return handballRowToPlayerMatchStatsConvertor;
            }
            default -> throw new IllegalStateException("Unexpected convertor type: " + convertorType);
        }
    }

}