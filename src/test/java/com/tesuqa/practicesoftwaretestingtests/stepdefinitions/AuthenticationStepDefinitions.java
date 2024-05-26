package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

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

    private Actor apiActor;

    @Before
    public void setTheStage() {
        apiActor = OnStage.theActorCalled("apiActor");
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables).getProperty("api.base.url");
        apiActor = OnStage.theActorCalled("ApiActor").whoCan(UseUsersApi.at(theRestApiBaseUrl));
    }

    @Given("the user is logged in with email {string} and password {string}")
    public void theUserIsLoggedInWithUsernameAndPassword(String email, String password) {
        apiActor.attemptsTo(Login.withEmailAndPassword(email, password));
        String token = apiActor.recall("token");
        System.out.println(token);
    }
}
