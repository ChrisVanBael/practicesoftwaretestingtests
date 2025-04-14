package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.ApiClientManager;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseInvoicesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseUsersApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.Login;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.thucydides.model.util.EnvironmentVariables;

public class AuthenticationStepDefinitions {

    private EnvironmentVariables environmentVariables;

    private Actor myActor;

    @Before(order = 10)
    public void prepareBrandActor() {
        // Access the existing actor via OnStage
        // No need to set API abilities again as they're already added in Hooks
        myActor = OnStage.theActorInTheSpotlight();
    }

    @Given("the user is logged in with email {string} and password {string}")
    public void theUserIsLoggedInWithUsernameAndPassword(String email, String password) {
        myActor.attemptsTo(Login.withEmailAndPassword(email, password));
        String token = myActor.recall("token");
        ApiClientManager.as(myActor).setAccessToken(token);
    }
}
