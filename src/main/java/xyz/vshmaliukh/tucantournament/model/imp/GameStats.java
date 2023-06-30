package xyz.vshmaliukh.tucantournament.model.imp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.vshmaliukh.tucantournament.model.IGameStats;
import xyz.vshmaliukh.tucantournament.model.imp.PlayerMatchStats;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStats implements IGameStats {

    private String sportType;

    private List<PlayerMatchStats> playerMatchStats = new ArrayList<>();

    @Override
    public List<PlayerMatchStats> getPlayerMatchStats() {
        return new ArrayList<>(playerMatchStats);
    }

}