package xyz.vshmaliukh.tucantournament.model;

/**
 * Author: vshmaliukh
 * Represents an (sport | game | match) action in a game.
 * This interface defines the properties and methods of an action.
 */
public interface IAction {

    /**
     * Gets the type of the action.
     *
     * @return the action type.
     */
    String getActionType();

    /**
     * Gets the points awarded for the action.
     *
     * @return the points per action.
     */
    int getPointsPerAction();

    /**
     * Gets the index of the action.
     *
     * @return the action index.
     */
    int getIndex();

    /**
     * Sets the type of the action.
     *
     * @param actionType the action type to set.
     */
    void setActionType(String actionType);

    /**
     * Sets the points awarded for the action.
     *
     * @param pointsPerAction the points per action to set.
     */
    void setPointsPerAction(int pointsPerAction);

    /**
     * Sets the index of the action.
     *
     * @param index the action index to set.
     */
    void setIndex(int index);

}
