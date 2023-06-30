package xyz.vshmaliukh.tucantournament.model.imp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.vshmaliukh.tucantournament.model.IGameStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: vshmaliukh
 * Represents game stats performed in a tournament.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStats implements IGameStats {

    private String sportType;

    private List<PlayerMatchStats> playerMatchStats = new ArrayList<>();

    /**
     * Returns a new copy of the player match statistics list.
     *
     * @return a new copy of the player match statistics list
     */
    @Override
    public List<PlayerMatchStats> getPlayerMatchStats() {
        return new ArrayList<>(playerMatchStats);
    }

}