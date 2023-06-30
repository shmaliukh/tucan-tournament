package xyz.vshmaliukh.tucantournament.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.vshmaliukh.tucantournament.model.Action;

import java.util.*;

@Component
public class SportActionsProvider {

    public static final String SPORT_BASKETBALL_STR = "basketball";
    public static final String SPORT_HANDBALL_STR = "handball";

    public final Map<String, Set<Action>> sportActionsMap = new HashMap<>();

    // BASKETBALL
    @Value("${sport.basketball.score_point.row_index}")
    public int basketballScorePointIndex;
    @Value("${sport.basketball.rebound.row_index}")
    public int basketballReboundIndex;
    @Value("${sport.basketball.assist.row_index}")
    public int basketballAssistIndex;

    // HANDBALL
    @Value("${sport.handball.goal_made.row_index}")
    public int handballGoalMadeIndex = 4;
    @Value("${sport.handball.goal_received.row_index}")
    public int handballGoalReceivedIndex = 5;


    public SportActionsProvider() {
        initializeSportActions();
    }

    private void initializeSportActions() {
        initBasketballActions();
        initHandballActions();
    }

    private void initHandballActions() {
        // HANDBALL
        Set<Action> handballActionSet = new HashSet<>();

        handballActionSet.add(new Action("goal made", 2, handballGoalMadeIndex));
        handballActionSet.add(new Action("goal received", -1, handballGoalReceivedIndex));

        sportActionsMap.put(SPORT_HANDBALL_STR, handballActionSet);
    }

    private void initBasketballActions() {
        Set<Action> basketballActionSet = new HashSet<>();

        basketballActionSet.add(new Action("scored point", 2, basketballScorePointIndex));
        basketballActionSet.add(new Action("rebound", 1, basketballReboundIndex));
        basketballActionSet.add(new Action("assist", 1, basketballAssistIndex));

        sportActionsMap.put(SPORT_BASKETBALL_STR, basketballActionSet);
    }

    public Optional<Action> getActionBySportAndPosition(String sport, int position) {
        // TODO refactor actions provider structure
        return sportActionsMap.get(sport).stream()
                .filter(action -> action.getIndex() == position)
                .findFirst();
    }

}
