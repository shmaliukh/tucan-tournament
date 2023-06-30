package xyz.vshmaliukh.tucantournament.model;

public interface IAction {
    String getActionType();

    int getPointsPerAction();

    int getIndex();

    void setActionType(String actionType);

    void setPointsPerAction(int pointsPerAction);

    void setIndex(int index);
}
