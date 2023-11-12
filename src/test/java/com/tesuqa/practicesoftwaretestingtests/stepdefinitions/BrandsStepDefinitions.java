package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.AddBrand;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteBrand;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.model.util.EnvironmentVariables;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheBrandNames;

import java.util.List;

public class BrandsStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor apiActor;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables).getProperty("api.base.url");
        apiActor = Actor.named("ApiActor").whoCan(UseBrandsApi.at(theRestApiBaseUrl));
    }

    /**
     * Verifies the brand name doesn't exist yet. If it exists, deletes it
     * @param brandName name of the brand
     */
    @Given("the {string} is not entered yet")
    public void assureBrandNotEntered(String brandName) {
        List<String> allBrandNames = apiActor.asksFor(TheBrandNames.knownByTheSystem());
        System.out.println("Before delete: " + allBrandNames);
        if (allBrandNames.contains(brandName)) {
            apiActor.attemptsTo(DeleteBrand.withName(brandName));
        }
        System.out.println("After delete: " + allBrandNames);
    }

    /**
     * Adds a brand with the name and slug
     * @param brandName name of the brand
     * @param brandSlug slug of the brand
     */
    @When("I add brand with name {string} and slug {string}")
    public void addBrand(String brandName, String brandSlug) {
        apiActor.attemptsTo(AddBrand.withNameAndSlug(brandName, brandSlug));
    }

    /**
     * Verifies that the brand is in the system
     * @param brandName the name of the brand
     */
    @Then("the {string} brand is available")
    public void verifyBrandAvailable(String brandName) {
        List<String> allBrandNames = apiActor.asksFor(TheBrandNames.knownByTheSystem());
        apiActor.attemptsTo(Ensure.that(brandName).isIn(allBrandNames));
    }


}
