package pl.gda.spc;

import java.util.List;

import pl.gda.spc.model.Player;
import pl.gda.spc.model.PlayerFieldDefinition;
import pl.gda.spc.model.PlayerQuizAnswer;
import pl.gda.spc.model.QuestionWrapper;

public interface DevcrowdCompService {

    QuestionWrapper getQuestionsValues();

    int validateAnswers(PlayerQuizAnswer playerAnswers);

    boolean registerFieldForPlayer(PlayerFieldDefinition field);

    boolean isEmailValid(String email);

    List<Player> getAllPlayers();
}
