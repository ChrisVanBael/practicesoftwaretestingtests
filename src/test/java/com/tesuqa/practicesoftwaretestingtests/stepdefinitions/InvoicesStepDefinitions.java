package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.practicesoftwaretesting.client.v5.model.InvoiceResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseInvoicesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheInvoices;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.List;

public class InvoicesStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor myActor;

    @Before(order = 10)
    public void prepareBrandActor() {
        // Access the existing actor via OnStage
        // No need to set API abilities again as they're already added in Hooks
        myActor = OnStage.theActorInTheSpotlight();
    }

    @When("the user retrieves the invoices")
    public void theUserRetrievesTheInvoices() {
        String token = myActor.recall("token");
        System.out.println(token);
        List<InvoiceResponse> allInvoices = myActor.asksFor(TheInvoices.knownByTheSystem());
        myActor.remember("invoices", allInvoices);
    }

    @Then("the number of invoices should be greater than {int}")
    public void invoicesShouldBeSuccessfullyRetrieved(int numberOfInvoices) {
        myActor.attemptsTo(Ensure.that(numberOfInvoices).isLessThanOrEqualTo(((List<InvoiceResponse>) myActor.recall("invoices")).size()));
    }
}
