package com.eaitest.definition;

import org.openqa.selenium.WebDriver;

import com.eaitest.task.HomePageTask;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Managed;

public class HomePageDefinitions {
	
	@Managed(driver = "chrome",  options="--start-maximized --ignore-certificate-errors --disable-infobars ")
	WebDriver webDriver;
	
	@Given("^Abrir navegador$")
	public void abrirNavegador(){
		OnStage.setTheStage(new OnlineCast());
		OnStage.theActorCalled("david").can(BrowseTheWeb.with(webDriver));
	}

	@When("^Abrir pagina principal$")
	public void abrirPaginaPrincipal() {
		OnStage.theActorInTheSpotlight().attemptsTo(HomePageTask.openBrowser());
	}
}