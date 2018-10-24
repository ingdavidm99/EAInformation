package com.eaitest.definitions;

import org.openqa.selenium.WebDriver;

import com.eaitest.task.TaskTest;
import com.eaitest.ui.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Managed;

public class DefinitionsTest {
	
	@Managed(driver = "chrome")
	WebDriver webDriver;
	
	Actor actor = Actor.named("david");
	
	@Given("^Ingresar a la pagina$")
	public void ingresarALaPagina() {
		actor.can(BrowseTheWeb.with(webDriver));
		actor.wasAbleTo(Open.browserOn(new HomePage()));
	}

	@When("^ingresar credenciales (.*) y (.*)$")
	public void ingresarCredencialesY(String userName, String password) {
		actor.attemptsTo(TaskTest.with(userName, password));
	}

	@Then("^errror$")
	public void errror() {
	}
}
