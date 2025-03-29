package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheCategoryNames;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheId;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.AddCategory;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteCategory;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.List;

public class CategoryStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor myActor;

    @Before(order = 10)
    public void prepareBrandActor() {
        // Access the existing actor via OnStage
        // No need to set API abilities again as they're already added in Hooks
        myActor = OnStage.theActorInTheSpotlight();
        myActor.remember("categories", 2);
    }

    /**
     * Verifies the category name doesn't exist yet. If it exists, deletes it
     * @param categoryName name of the category
     */
    @Given("the {string} category is not entered yet")
    public void assureCategoryNotEntered(String categoryName) {
        List<String> allCategoryNames = myActor.asksFor(TheCategoryNames.knownByTheSystem());
        if (allCategoryNames.contains(categoryName)) {
            myActor.attemptsTo(DeleteCategory.withName(categoryName));
        }
    }

    /**
     * Adds a category with the name and slug
     * @param categoryName name of the category
     * @param categorySlug slug of the category
     */
    @When("I add category with name {string} and slug {string}")
    public void addCategoryWithSlug(String categoryName, String categorySlug) {
        myActor.attemptsTo(AddCategory.withNameAndSlug(categoryName, categorySlug));
    }

    /**
     * Verifies that the category is in the system
     * @param categoryName
     */
    @Then("the {string} category is available")
    public void verifyCategoryAvailable(String categoryName) {
        List<String> allCategoryNames = myActor.asksFor(TheCategoryNames.knownByTheSystem());
        myActor.attemptsTo(Ensure.that(categoryName).isIn(allCategoryNames));
    }


    /**
     * Add a new category, verifies first if it exists, if it does, it deletes it
     * @param categoryName the name of the category to add
     * <b>Memory Read</b>: "nothing" <br>
     * <b>Memory Write</b>: "CategoryId" <br>
     */
    @When("I add the new category {string}")
    public void addCategory(String categoryName) {
        assureCategoryNotEntered(categoryName);
        myActor.attemptsTo(AddCategory.withNameAndSlug(categoryName, categoryName.replace(' ', '-')));
        Integer categoryId = myActor.asksFor(TheId.ofCategory(categoryName));
        myActor.remember("CategoryId", categoryId);
    }


}
