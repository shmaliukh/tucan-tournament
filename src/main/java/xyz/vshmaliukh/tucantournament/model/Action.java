package xyz.vshmaliukh.tucantournament.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    private String actionType;
    private int pointsPerAction;

    private int index;

}
