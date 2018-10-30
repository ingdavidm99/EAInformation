package com.eaitest.ui;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class Menu extends PageObject {
	public static final Target BUTTON_PARENT_MENU = Target.the("Mensaje de error").located(By.className("leftmenutrigger"));
	public static final Target BUTTON_MENU = Target.the("Mensaje de error {0}").locatedBy("//a[@id = '{0}']");
	public static final Target BUTTON_SUB_MENU = Target.the("Mensaje de error {0}").locatedBy("//a[@id = '{0}']");
}
