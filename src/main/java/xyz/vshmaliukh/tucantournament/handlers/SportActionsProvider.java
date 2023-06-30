package xyz.vshmaliukh.tucantournament.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.model.imp.Action;

import java.util.*;

/**
 * Author: vshmaliukh
 * Component that provides actions for different sports such as basketball and handball.
 */
@Component
public class SportActionsProvider {

    public static final String SPORT_BASKETBALL_STR = "basketball";
    public static final String SPORT_HANDBALL_STR = "handball";

    public final Map<String, Set<Action>> sportActionsMap = new HashMap<>();

    SportDataConfig sportDataConfig;

    public SportActionsProvider(SportDataConfig sportDataConfig) {
        this.sportDataConfig = sportDataConfig;
        initializeSportActions();
    }

    /**
     * Initializes the sport actions for basketball and handball.
     */
    private void initializeSportActions() {
        initBasketballActions();
        initHandballActions();
    }

    private void initHandballActions() {
        // HANDBALL
        Set<Action> handballActionSet = new HashSet<>();

        handballActionSet.add(new Action("goal made", sportDataConfig.handballGoalMadePoints, sportDataConfig.handballGoalMadeIndex));
        handballActionSet.add(new Action("goal received", sportDataConfig.handballGoalReceivedPoints, sportDataConfig.handballGoalReceivedIndex));

        sportActionsMap.put(SPORT_HANDBALL_STR, handballActionSet);
    }

    private void initBasketballActions() {
        Set<Action> basketballActionSet = new HashSet<>();

        basketballActionSet.add(new Action("scored point", sportDataConfig.basketballScorePointPoints, sportDataConfig.basketballScorePointIndex));
        basketballActionSet.add(new Action("rebound", sportDataConfig.basketballReboundIndex, sportDataConfig.basketballReboundIndex));
        basketballActionSet.add(new Action("assist", sportDataConfig.basketballAssistIndex, sportDataConfig.basketballAssistIndex));

        sportActionsMap.put(SPORT_BASKETBALL_STR, basketballActionSet);
    }

    /**
     * Retrieves the action by sport and position (index).
     *
     * @param sportStr the sport string ("basketball", "handball", ...)
     * @param index    the position (index) of the action
     * @return an Optional containing the Action if found, or an empty Optional if not found
     */
    public Optional<Action> getActionBySportAndPosition(String sportStr, int index) {
        // TODO refactor actions provider structure
        return sportActionsMap.get(sportStr.toLowerCase()).stream()
                .filter(action -> action.getIndex() == index)
                .findFirst();
    }


    /**
     * Class contains configuration properties related to different sports,
     * such as player match statistics indices for name, number, team name,
     * and other specific indices for basketball and handball.
     */
    @Configuration
    public static class SportDataConfig {

        // Player Match Stats
        @Value("${sport.player.name.row_index:#{0}}")
        public int playerMatchStatsNameIndex;
        @Value("${sport.player.nick.row_index:#{1}}")
        public int playerMatchStatsNickIndex;
        @Value("${sport.player.number.row_index:#{2}}")
        public int playerMatchStatsNumberIndex;
        @Value("${sport.player.team_name.row_index:#{3}}")
        public int playerMatchStatsTeamNameIndex;

        // BASKETBALL
        @Value("${sport.basketball.score_point.row_index:#{4}}")
        public int basketballScorePointIndex;
        @Value("${sport.basketball.rebound.row_index:#{5}}")
        public int basketballReboundIndex;
        @Value("${sport.basketball.assist.row_index:#{6}}")
        public int basketballAssistIndex;

        @Value("${sport.basketball.score_point.points:#{2}}")
        public int basketballScorePointPoints;
        @Value("${sport.basketball.rebound.points:#{1}}")
        public int basketballReboundPoints;
        @Value("${sport.basketball.assist.points:#{1}}")
        public int basketballAssistPoints;

        // HANDBALL
        @Value("${sport.handball.goal_made.row_index:#{4}}")
        public int handballGoalMadeIndex;
        @Value("${sport.handball.goal_received.row_index:#{5}}")
        public int handballGoalReceivedIndex;

        @Value("${sport.handball.goal_made.points:#{2}}")
        public int handballGoalMadePoints;
        @Value("${sport.handball.goal_received.points:#{-1}}")
        public int handballGoalReceivedPoints;

    }

}
