package pl.gda.spc.model;

import lombok.Getter;
import lombok.Setter;


public class Answer {

	@Getter
	@Setter
	private String answer;

	@Getter
	@Setter
	private String value;

	@Getter
	@Setter
	private boolean correct = false;

}
