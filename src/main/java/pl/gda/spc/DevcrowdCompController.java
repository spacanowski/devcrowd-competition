package pl.gda.spc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.gda.spc.model.Player;
import pl.gda.spc.model.PlayerFieldDefinition;
import pl.gda.spc.model.PlayerQuizAnswer;
import pl.gda.spc.model.QuestionWrapper;

@RestController
public class DevcrowdCompController {

    @Autowired
    private DevcrowdCompService service;

    @RequestMapping(value="/ques", method=RequestMethod.GET)
    public QuestionWrapper getQuestions() {
        return service.getQuestionsValues();
    }

    @RequestMapping(value="/players", method=RequestMethod.GET)
    public List<Player> getAllPlayers() {
        return service.getAllPlayers();
    }

    @RequestMapping(value="/field", method=RequestMethod.POST)
    public boolean registerField(@RequestBody PlayerFieldDefinition field) {
        return service.registerFieldForPlayer(field);
    }

    @RequestMapping(value="/ques", method=RequestMethod.POST)
    public int registerAnswer(@RequestBody PlayerQuizAnswer answer) {
        if (answer.getEmail() != null && service.isEmailValid(answer.getEmail())) {
            return service.validateAnswers(answer);
        }

        return 0;
    }

}
