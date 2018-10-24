package com.eaitest.ui;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("http://localhost:8080/eai/signin")
public class HomePage extends PageObject {

	public static final Target INPUT_USERNAME = Target.the("Campo texto username").located(By.name("username"));
	public static final Target INPUT_PASSWORD = Target.the("Campo texto password").located(By.name("password"));
	
	public static final Target BUTTON_LOGIN = Target.the("Boton signin").located(By.name("signin"));
}
