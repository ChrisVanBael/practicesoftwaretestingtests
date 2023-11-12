package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheCategoryNames;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.AddCategory;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteCategory;
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

import java.util.List;

public class CategoryStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor apiActor;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables).getProperty("api.base.url");
        apiActor = Actor.named("ApiActor").whoCan(UseCategoriesApi.at(theRestApiBaseUrl));
    }

    /**
     * Verifies the category name doesn't exist yet. If it exists, deletes it
     * @param categoryName name of the category
     */
    @Given("the {string} category is not entered yet")
    public void assureCategoryNotEntered(String categoryName) {
        List<String> allCategoryNames = apiActor.asksFor(TheCategoryNames.knownByTheSystem());
        if (allCategoryNames.contains(categoryName)) {
            apiActor.attemptsTo(DeleteCategory.withName(categoryName));
        }
    }

    /**
     * Adds a category with the name and slug
     * @param categoryName name of the category
     * @param categorySlug slug of the category
     */
    @When("I add category with name {string} and slug {string}")
    public void addCategory(String categoryName, String categorySlug) {
        apiActor.attemptsTo(AddCategory.withNameAndSlug(categoryName, categorySlug));
    }

    /**
     * Verifies that the category is in the system
     * @param categoryName
     */
    @Then("the {string} category is available")
    public void verifyCategoryAvailable(String categoryName) {
        List<String> allCategoryNames = apiActor.asksFor(TheCategoryNames.knownByTheSystem());
        apiActor.attemptsTo(Ensure.that(categoryName).isIn(allCategoryNames));
    }


}
