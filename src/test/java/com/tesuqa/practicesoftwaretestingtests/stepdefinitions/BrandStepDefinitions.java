package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.practicesoftwaretesting.client.model.BrandResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheBrands;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheId;
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
import net.thucydides.model .util.EnvironmentVariables;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheBrandNames;

import java.util.List;

public class BrandStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor myActor;

    @Before
    public void setTheStage() {
        myActor = OnStage.theActorCalled("myActor");
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables).getProperty("api.base.url");
        myActor.whoCan(UseBrandsApi.at(theRestApiBaseUrl));
    }

    /**
     * Verifies the brand name doesn't exist yet. If it exists, deletes it
     * @param brandName name of the brand
     */
    @Given("the {string} brand is not entered yet")
    public void assureBrandNotEntered(String brandName) {
        List<String> allBrandNames = myActor.asksFor(TheBrandNames.knownByTheSystem());
        if (allBrandNames.contains(brandName)) {
            myActor.attemptsTo(DeleteBrand.withName(brandName));
        }
    }

    /**
     * Adds a brand with the name and slug
     * @param brandName name of the brand
     * @param brandSlug slug of the brand
     */
    @When("I add brand with name {string} and slug {string}")
    public void addBrandWithSlug(String brandName, String brandSlug) {
        myActor.attemptsTo(AddBrand.withNameAndSlug(brandName, brandSlug));
    }

    /**
     * Verifies that the brand is in the system
     * @param brandName the name of the brand
     */
    @Then("the {string} brand is available")
    public void verifyBrandAvailable(String brandName) {
        List<String> allBrandNames = myActor.asksFor(TheBrandNames.knownByTheSystem());
        myActor.attemptsTo(Ensure.that(brandName).isIn(allBrandNames));
    }

    /**
     * Add a new brand, verifies first if it exists, if it does, it deletes it
     * @param brandName the name of the brand to add
     * <b>Memory Read</b>: "nothing" <br>
     * <b>Memory Write</b>: "BrandId" <br>
     */
    @When("I add the new brand {string}")
    public void addBrand(String brandName) {
        assureBrandNotEntered(brandName);
        myActor.attemptsTo(AddBrand.withNameAndSlug(brandName, brandName.replace(' ', '-')));
        Integer brandId = myActor.asksFor(TheId.ofBrand(brandName));
        myActor.remember("BrandId", brandId);
    }


}
