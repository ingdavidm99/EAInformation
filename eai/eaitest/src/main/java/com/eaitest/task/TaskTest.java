package com.eaitest.task;

import com.eaitest.ui.HomePage;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class TaskTest implements Task {

	private String userName;
	
	private String password;	

	public TaskTest(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public <T extends Actor> void performAs(T actor) {
		actor.attemptsTo
			(
				Enter.theValue(userName).into(HomePage.INPUT_USERNAME),
				Enter.theValue(password).into(HomePage.INPUT_PASSWORD),
				Click.on(HomePage.BUTTON_LOGIN)
			);
	}
	
	public static TaskTest with(String userName, String password) {
		return Tasks.instrumented(TaskTest.class, userName, password);
	}
}
