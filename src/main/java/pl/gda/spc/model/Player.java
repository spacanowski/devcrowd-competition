package pl.gda.spc.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Player {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private int points;
//    @Getter
//    @Setter
//    private boolean disqualified;
    @Getter
    @Setter
    private List<Cell> cells;

}
