package com.eaitest.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		features = "src/test/resources/features/homePage.feature",
		snippets = SnippetType.CAMELCASE,
		glue = "com.eaitest.definition",
		plugin = {"pretty", "html:target/cucumber-html-report"})
public class HomePageRunner {

}
