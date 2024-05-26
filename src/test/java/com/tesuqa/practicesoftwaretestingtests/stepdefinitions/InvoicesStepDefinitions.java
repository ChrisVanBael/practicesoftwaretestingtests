package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.practicesoftwaretesting.client.model.InvoiceResponse;
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
    private Actor apiActor;

    @Before
    public void setTheStage() {
        apiActor = OnStage.theActorCalled("apiActor");
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables).getProperty("api.base.url");
        this.apiActor = OnStage.theActorCalled("ApiActor").whoCan(UseInvoicesApi.at(theRestApiBaseUrl));
    }

    @When("the user retrieves the invoices")
    public void theUserRetrievesTheInvoices() {
        String token = apiActor.recall("token");
        System.out.println(token);
        List<InvoiceResponse> allInvoices = apiActor.asksFor(TheInvoices.knownByTheSystem());
        apiActor.remember("invoices", allInvoices);
    }

    @Then("{int} invoices should be successfully retrieved")
    public void invoicesShouldBeSuccessfullyRetrieved(int numberOfInvoices) {
        apiActor.attemptsTo(Ensure.that(numberOfInvoices).isEqualTo(((List<InvoiceResponse>) apiActor.recall("invoices")).size()));
    }
}
