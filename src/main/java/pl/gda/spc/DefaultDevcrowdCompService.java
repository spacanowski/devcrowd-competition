package pl.gda.spc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.gda.spc.model.Player;
import pl.gda.spc.model.PlayerFieldDefinition;
import pl.gda.spc.model.PlayerQuizAnswer;
import pl.gda.spc.model.PlayerQuizAnswer.Answer;
import pl.gda.spc.model.Question;
import pl.gda.spc.model.QuestionWrapper;

@Service
public class DefaultDevcrowdCompService implements DevcrowdCompService {

    @Autowired
    private DevcrowdDao dao;

    @Value("#{questions}")
    private List<Question> questions;

    @Override
    public QuestionWrapper getQuestionsValues() {
        return new QuestionWrapper(questions);
    }

    @Override
    public int validateAnswers(PlayerQuizAnswer playerAnswers) {
        int result = 0;

        for (Answer playerAnswer :  playerAnswers.getAnswers()) {
            Optional<Question> matchedQuestion = questions.stream()
                    .filter(q -> q.getQuestionId() == playerAnswer.getQuestionNumber()).findFirst();

            if (matchedQuestion.isPresent()) {
                Optional<pl.gda.spc.model.Answer> foundAnswer = matchedQuestion.get().getAnswers().stream()
                        .filter(a -> areAnswersEqual(a, playerAnswer)).findFirst();

                if (foundAnswer.isPresent() && foundAnswer.get().isCorrect()) {
                    result += 1;
                }
            }
        }

        saveResult(playerAnswers.getName(), playerAnswers.getEmail(), result);

        return result;
    }

    private boolean areAnswersEqual(pl.gda.spc.model.Answer reference, Answer checked) {
        return reference.getValue().equals(checked.getSelectedAnswer());
    }

    @Transactional
    private void saveResult(String name, String email, int points) {
        dao.insertPlayer(name, email, points);
    }

    @Transactional(readOnly=true)
    private List<Player> getPlayerByEmail(String email) {
        return dao.getPlayersByEmail(email);
    }

    @Transactional(readOnly=true)
    @Override
    public boolean isEmailValid(String email) {
        return dao.getEmailsCount(email) == 0;
    }

    @Transactional
    @Override
    public boolean registerFieldForPlayer(PlayerFieldDefinition field) {
        List<Player> players = getPlayerByEmail(field.getEmail());

        if (isRequestNotValid(field, players)) {
            return false;
        }

        Player player = players.get(0);

        if (player.getId() != null && !field.getCells().isEmpty()) {
            dao.insertField(player.getId(), field);
        }

        return true;
    }

    private boolean isRequestNotValid(PlayerFieldDefinition field, List<Player> players) {
        return players.isEmpty() || players.size() > 2 || field.getCells() == null
                || field.getCells().size() != players.get(0).getPoints();
    }

    @Override
    public List<Player> getAllPlayers() {
        List<Player> players = dao.getAllPlayers();

        players.stream().forEach(this::addCellsFromDbToPlayer);

        return players;
    }

    @Transactional(readOnly=true)
    private void addCellsFromDbToPlayer(Player player) {
        player.setCells(dao.getCellsForPlayer(player.getId()));
    }

}
