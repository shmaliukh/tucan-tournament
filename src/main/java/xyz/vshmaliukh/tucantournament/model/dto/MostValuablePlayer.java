package xyz.vshmaliukh.tucantournament.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MostValuablePlayer {

    private String nickName;
    private Long rating;

}
