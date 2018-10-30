package com.eaitest.definition;

import org.openqa.selenium.WebDriver;

import com.eaitest.question.SignInQuestion;
import com.eaitest.task.SignInTask;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.thucydides.core.annotations.Managed;

public class SignInDefinitions {
	
	@Managed(driver = "chrome",  options="--start-maximized --ignore-certificate-errors --disable-infobars ")
	WebDriver webDriver;
	
	@When("^Enter wrong credentials (.*) (.*)$")
	public void enterWrongCredentials(String userName, String password) {
		OnStage.theActorInTheSpotlight().attemptsTo(SignInTask.with(userName, password));
	}

	@Then("^Error message$")
	public void errorMessage() {
		OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(SignInQuestion.validateTheAnswer("failed")));
	}
	
	
	@When("^Enter correct credentials (.*) (.*)$")
	public void enterCorrectCredentials(String userName, String password) {
		OnStage.theActorInTheSpotlight().attemptsTo(SignInTask.with(userName, password));
	}

	@Then("^Successful message$")
	public void successfulMessage() {
		OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(SignInQuestion.validateTheAnswer("successful")));
	}

}