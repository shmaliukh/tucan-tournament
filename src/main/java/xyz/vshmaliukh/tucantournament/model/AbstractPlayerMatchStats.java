package xyz.vshmaliukh.tucantournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.vshmaliukh.tucantournament.model.imp.Action;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@NoArgsConstructor
public abstract class AbstractPlayerMatchStats implements IPlayerMatchStats {

    protected String playerName;
    protected String nickname;
    protected String sportType;
    protected String teamName;
    protected int number;

    protected int currentMatchPoints;

    protected boolean isWinner;

    protected Map<Action, Integer> actionCounterMap = new HashMap<>();

    @Override
    public void addAction(Action action, int counter) {
        actionCounterMap.put(action, counter);
        log.debug("added new actions '{}', actions counter: '{}' to player with nickname '{}'", action, counter, nickname);
    }

    @Override
    public int calcCurrentMatchPlayerPoints() {
        currentMatchPoints = 0;
        int pointsPerAction;
        int counter;
        for (Map.Entry<Action, Integer> entry : actionCounterMap.entrySet()) {
            Action action = entry.getKey();
            counter = entry.getValue();
            pointsPerAction = action.getPointsPerAction();
            currentMatchPoints += pointsPerAction * counter;
        }
        return currentMatchPoints;
    }

}
