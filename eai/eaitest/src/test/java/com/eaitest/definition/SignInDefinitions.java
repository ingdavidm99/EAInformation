package com.eaitest.definition;

import org.openqa.selenium.WebDriver;

import com.eaitest.question.SignInQuestion;
import com.eaitest.task.SignInTask;
import com.eaitest.ui.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Managed;

public class SignInDefinitions {
	
	@Managed(driver = "chrome",  options="--start-maximized --ignore-certificate-errors --disable-infobars ")
	WebDriver webDriver;
	
	@Given("^Abrir pagina$")
	public void abrirPagina() {
		OnStage.setTheStage(new OnlineCast());
		OnStage.theActorCalled("david").can(BrowseTheWeb.with(webDriver));
		OnStage.theActorInTheSpotlight().wasAbleTo(Open.browserOn(new HomePage()));
	}

	@When("^Ingresar credenciales failed (.*) (.*)$")
	public void ingresarCredencialesFailed(String userName, String password) {
		OnStage.theActorInTheSpotlight().attemptsTo(SignInTask.with(userName, password));
	}

	@Then("^failed$")
	public void failed() {
		OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(SignInQuestion.errorFailed("failed")));
	}
	
	
	@When("^Ingresar credenciales successful (.*) (.*)$")
	public void ingresarCredencialesSuccessful(String userName, String password) {
		OnStage.theActorInTheSpotlight().attemptsTo(SignInTask.with(userName, password));
	}

	@Then("^successful$")
	public void successful() {
		OnStage.theActorInTheSpotlight().should(GivenWhenThen.seeThat(SignInQuestion.errorFailed("successful")));
	}

}