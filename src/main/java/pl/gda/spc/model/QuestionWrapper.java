package pl.gda.spc.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class QuestionWrapper {

	@Getter
	@Setter
	private List<Question> questions;

}
