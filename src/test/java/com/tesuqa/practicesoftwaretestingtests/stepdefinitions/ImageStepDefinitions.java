package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;


import com.practicesoftwaretesting.client.v5.model.ImageResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheImages;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ImageStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor myActor;
    private WebDriver browser;

    @Before(order = 10)
    public void prepareBrandActor() {
        // Access the existing actor via OnStage
        // No need to set API abilities again as they're already added in Hooks
        myActor = OnStage.theActorInTheSpotlight();
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
