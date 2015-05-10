package pl.gda.spc.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PlayerQuizAnswer {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private List<Answer> answers;

    public static class Answer {
        @Getter
        @Setter
        private int questionNumber;
        @Getter
        @Setter
        private String selectedAnswer;
    }

}
