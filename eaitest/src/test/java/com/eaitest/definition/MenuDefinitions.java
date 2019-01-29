package com.eaitest.definition;

import org.openqa.selenium.WebDriver;

import com.eaitest.task.MenuTask;

import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.thucydides.core.annotations.Managed;

public class MenuDefinitions {
	
	@Managed(driver = "chrome",  options="--start-maximized --ignore-certificate-errors --disable-infobars ")
	WebDriver webDriver;
	
	@When("^Enter the following menu (.*) (.*)$")
	public void enterTheFollowingMenu(String menu, String subMenu) {
		OnStage.theActorInTheSpotlight().attemptsTo(MenuTask.enterMenu(menu, subMenu));
	}
}