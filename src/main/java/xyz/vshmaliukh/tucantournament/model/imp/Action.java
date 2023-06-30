package xyz.vshmaliukh.tucantournament.model.imp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.vshmaliukh.tucantournament.model.IAction;

/**
 * Author: vshmaliukh
 * Represents an action performed in a tournament.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action implements IAction {

    private String actionType;
    private int pointsPerAction;

    // The index of the action at row
    private int index; // TODO need to refactor - action should not contain info about CSV

}
