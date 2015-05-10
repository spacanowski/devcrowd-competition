package pl.gda.spc.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PlayerFieldDefinition {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private List<Cell> cells;

}
