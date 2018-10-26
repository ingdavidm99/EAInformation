package com.eaitest.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		features = "src/test/resources/features/SignIn.feature",
		snippets = SnippetType.CAMELCASE,
		glue = "com.eaitest.definitions",
		plugin = {"pretty", "html:target/cucumber-html-report"})
public class RunnerSignIn {

}
