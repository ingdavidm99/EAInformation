package com.eaitest.ui;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class SignIn extends PageObject {
	public static final Target INPUT_USERNAME = Target.the("Campo texto username").located(By.name("username"));
	public static final Target INPUT_PASSWORD = Target.the("Campo texto password").located(By.name("password"));
	
	public static final Target BUTTON_LOGIN = Target.the("Boton signin").located(By.name("signin"));
	
	public static final Target FAILED = Target.the("Mensaje de error").located(By.tagName("span"));
	public static final Target SUCCESSFUL = Target.the("Mensaje de error").locatedBy("//a[@href = '/eai/index']");
}
