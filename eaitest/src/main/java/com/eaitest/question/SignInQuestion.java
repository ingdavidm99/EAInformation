package com.eaitest.question;

import com.eaitest.ui.SignIn;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

public class SignInQuestion implements Question<Boolean>{

	private String type;
	
	public SignInQuestion(String type) {
		this.type = type;
	}

	@Override
	public Boolean answeredBy(Actor actor) {
		if("failed".equals(type)) {
			return Visibility.of(SignIn.FAILED).viewedBy(actor).asBoolean();
		} else {
			return Visibility.of(SignIn.SUCCESSFUL).viewedBy(actor).asBoolean();
		}	
	}
	
	public static SignInQuestion validateTheAnswer(String type) {
		return new SignInQuestion(type);
	}

}
