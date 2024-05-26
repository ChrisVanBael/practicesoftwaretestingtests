package com.tesuqa.practicesoftwaretestingtests;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        glue = "com.tesuqa.practicesoftwaretestingtests.stepdefinitions"
)
public class CucumberTestSuite {}
