package com.eaitest.task;

import com.eaitest.ui.HomePage;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class HomePageTask implements Task {

	@Override
	public <T extends Actor> void performAs(T actor) {
		actor.attemptsTo(Open.browserOn(new HomePage()));
	}
	
	public static HomePageTask openMainWebPage() {
		return Tasks.instrumented(HomePageTask.class);
	}
}
