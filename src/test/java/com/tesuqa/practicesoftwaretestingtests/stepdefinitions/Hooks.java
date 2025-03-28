package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.practicesoftwaretesting.client.model.CategoryResponse;
import com.practicesoftwaretesting.client.model.ProductRequest;
import com.practicesoftwaretesting.client.model.ProductResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseProductsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteBrand;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteCategory;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteProduct;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.model.util.EnvironmentVariables;

public class Hooks {

    private EnvironmentVariables environmentVariables;

    private static Actor myActor;

    @BeforeAll
    public static void beforeAll() {
        OnStage.setTheStage(new OnlineCast());
        myActor = OnStage.theActorCalled("myActor");
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
        Integer categoryId = myActor.recall("CategoryId");
        if (categoryId != null) {
            myActor.whoCan(UseCategoriesApi.at(theRestApiBaseUrl));
            myActor.attemptsTo(DeleteCategory.withId(categoryId));
        }

        // Delete the created brand
        Integer brandId = myActor.recall("BrandId");
        if (brandId != null) {
            myActor.whoCan(UseBrandsApi.at(theRestApiBaseUrl));
            myActor.attemptsTo(DeleteBrand.withId(brandId));
        }
    }
}
