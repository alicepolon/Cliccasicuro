package cliccasicuro;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)

@CucumberOptions(
        format = {"pretty", "json-pretty:target/cucumber-report.json"},
        features = {"src/"})


public class CliccasicuroRunner {

}
