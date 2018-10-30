package com.eaitest.task;

import com.eaitest.ui.Menu;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class MenuTask implements Task {
	
	private String menu;
	private String subMenu;
	
	public MenuTask(String menu, String subMenu) {
		this.menu = menu;
		this.subMenu = subMenu;
	}

	@Override
	public <T extends Actor> void performAs(T actor) {
		actor.attemptsTo
			(
				Click.on(Menu.BUTTON_PARENT_MENU),
				Click.on(Menu.BUTTON_MENU.of(menu)),
				Click.on(Menu.BUTTON_SUB_MENU.of(subMenu))
			);
	}
	
	public static MenuTask enterMenu(String menu, String subMenu) {
		return Tasks.instrumented(MenuTask.class, menu, subMenu);
	}
}
