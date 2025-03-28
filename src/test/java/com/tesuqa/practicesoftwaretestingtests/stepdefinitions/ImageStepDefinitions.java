package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.practicesoftwaretesting.client.model.ImageResponse;
import com.practicesoftwaretesting.client.model.ProductRequest;
import com.practicesoftwaretesting.client.model.ProductResponse;
import com.tesuqa.practicesoftwaretestingtests.pages.HomePage;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseImagesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseProductsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheId;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheImages;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheProducts;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web.TheProductNames;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.AddProduct;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteProduct;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ImageStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor myActor;
    private WebDriver browser;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables).getProperty("api.base.url");
        myActor = Actor.named("MyActor");
        myActor.whoCan(UseImagesApi.at(theRestApiBaseUrl));
    }


    /**
     * Retrieves all images through the API
     * <b>Memory Read</b>: nothing <br>
     * <b>Memory Write</b>: "Images" <br>
     */
    @Given("all images are read through the API")
    public void followingProductIsNotEnteredYet() {
        List<ImageResponse> allImages = myActor.asksFor(TheImages.knownByTheSystem());
        myActor.remember("Images", allImages);
    }

    /**
     * Verifies the list of Images is not empty <br>
     * <b>Memory Read</b>: "Images" <br>
     * <b>Memory Write</b>: nothing <br>
     */
    @When("the list of images is not empty")
    public void verifyImageListNotEmpty() {
        List<ImageResponse> allImages = myActor.recall("Images");
        myActor.attemptsTo(Ensure.that(allImages).isNotNull());
    }
}
