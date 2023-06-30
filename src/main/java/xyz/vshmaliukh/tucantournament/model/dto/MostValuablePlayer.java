package xyz.vshmaliukh.tucantournament.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: vshmaliukh
 * Represents a Most Valuable Player (MVP).
 */
@Data
@AllArgsConstructor
public class MostValuablePlayer {

    private String nickName;
    private Long rating;

}
