package com.eaitest.task;

import com.eaitest.ui.SignIn;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class SignInTask implements Task {

	private String userName;
	
	private String password;	

	public SignInTask(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public <T extends Actor> void performAs(T actor) {
		actor.attemptsTo
			(
				Enter.theValue(userName).into(SignIn.INPUT_USERNAME),
				Enter.theValue(password).into(SignIn.INPUT_PASSWORD),
				Click.on(SignIn.BUTTON_LOGIN)
			);
	}
	
	public static SignInTask with(String userName, String password) {
		return Tasks.instrumented(SignInTask.class, userName, password);
	}
}
