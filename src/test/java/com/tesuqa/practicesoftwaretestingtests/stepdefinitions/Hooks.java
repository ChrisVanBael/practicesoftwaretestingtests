package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.practicesoftwaretesting.client.v5.model.CategoryResponse;
import com.practicesoftwaretesting.client.v5.model.ProductRequest;
import com.practicesoftwaretesting.client.v5.model.ProductResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.*;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteBrand;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteCategory;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteProduct;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private EnvironmentVariables environmentVariables;
    private static Actor myActor;
    private WebDriver browser;

    @BeforeAll
    public static void beforeAll() {
        // Ensure actor is initialized only once
        OnStage.setTheStage(new OnlineCast());
    }

    @Before(order = 1)
    public void prepareBaseActor() {
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
            .from(environmentVariables).getProperty("api.base.url");

        // This will create the actor if it doesn't exist or return existing one
        OnStage.theActorCalled("myActor")
            .whoCan(UseBrandsApi.at(theRestApiBaseUrl))
            .whoCan(UseCategoriesApi.at(theRestApiBaseUrl))
            .whoCan(UseImagesApi.at(theRestApiBaseUrl))
            .whoCan(UseInvoicesApi.at(theRestApiBaseUrl))
            .whoCan(UseProductsApi.at(theRestApiBaseUrl))
            .whoCan(UseUsersApi.at(theRestApiBaseUrl))
            .whoCan(BrowseTheWeb.with(browser));

        myActor = OnStage.theActorInTheSpotlight();

        // Add base memories that should be available in all step definition classes
        myActor.remember("baseSetupComplete", true);
    }


    @After
    public void cleanUp() {
        //Actor myActor = OnStage.theActorCalled("myActor");
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
            .from(environmentVariables).getProperty("api.base.url");

        // Delete the created product
        ProductRequest product = myActor.recall("New Product") ;
        if (product != null) {
            myActor.whoCan(UseProductsApi.at(theRestApiBaseUrl));
            myActor.attemptsTo(DeleteProduct.withName(product.getName()));
        }

        // Delete the created category
        String categoryId = myActor.recall("CategoryId");
        if (categoryId != null) {
            myActor.whoCan(UseCategoriesApi.at(theRestApiBaseUrl));
            myActor.attemptsTo(DeleteCategory.withId(categoryId));
        }

        // Delete the created brand
        String brandId = myActor.recall("BrandId");
        if (brandId != null) {
            myActor.whoCan(UseBrandsApi.at(theRestApiBaseUrl));
            myActor.attemptsTo(DeleteBrand.withId(brandId));
        }
    }
}
