package xyz.vshmaliukh.tucantournament.model.imp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.vshmaliukh.tucantournament.model.IAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action implements IAction {

    private String actionType;
    private int pointsPerAction;

    private int index;

}
