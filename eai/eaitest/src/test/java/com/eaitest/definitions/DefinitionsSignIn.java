package com.eaitest.definitions;

import org.openqa.selenium.WebDriver;

import com.eaitest.task.TaskSignIn;
import com.eaitest.ui.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Managed;

public class DefinitionsSignIn {
	
	@Managed(driver = "chrome",  options="--start-maximized --ignore-certificate-errors --disable-infobars ")
	WebDriver webDriver;
	
	@Given("^Ingresar a la pagina$")
	public void ingresarALaPagina() {
		OnStage.setTheStage(new OnlineCast());
		OnStage.theActorCalled("david").can(BrowseTheWeb.with(webDriver));
		OnStage.theActorInTheSpotlight().wasAbleTo(Open.browserOn(new HomePage()));
	}


	@When("^Ingresar credenciales (.*) (.*)$")
	public void ingresarCredenciales(String userName, String password){
		OnStage.theActorInTheSpotlight().attemptsTo(TaskSignIn.with(userName, password));
	}

	@Then("^Errror$")
	public void errror() {
	}
}