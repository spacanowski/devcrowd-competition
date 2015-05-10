package pl.gda.spc.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Question {

	@Getter
	@Setter
	private String question;

	@Getter
	@Setter
	private int questionId;

	@Getter
	@Setter
	private List<Answer> answers;

}
