package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.InvoiceApi;
import com.practicesoftwaretesting.client.v5.model.InvoiceResponse;
import com.practicesoftwaretesting.client.v5.model.PaginatedInvoiceResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.ArrayList;
import java.util.List;

public class UseInvoicesApi implements Ability {
    private String baseUrl;
    private InvoiceApi invoiceApi;


    private UseInvoicesApi(String baseUrl) {
        this.baseUrl = baseUrl;

        // Initialize the ApiClientManager with the base URL if not already initialized
        this.invoiceApi = ApiClientManager.getInstance(baseUrl).getApiClient().invoice();
    }

    /**
     * Used to access the Actor's ability to UseUsersApi from within the Interaction classes, such as GET or PUT
     * @param actor actor to use
     * @return UseUsersApi
     */
    public static UseInvoicesApi as(Actor actor) {
        return actor.abilityTo(UseInvoicesApi.class);
    }

    /**
     * Ability to Use the Products API at a specified URL
     * @param baseUrl URL to use
     * @return UseUsersApi
     */
    public static UseInvoicesApi at(String baseUrl) {
        return new UseInvoicesApi(baseUrl);
    }

    public String toString() {
        return "call the Invoices API at "+ baseUrl;
    }

    /**
     * Refresh the API client - call this if the token has been updated
     */
    public UseInvoicesApi refreshApiClient() {
        this.invoiceApi = ApiClientManager.getInstance(baseUrl).getApiClient().invoice();
        return this;
    }

    public List<InvoiceResponse> getAllInvoices() {
        Integer lastPage = 0;
        List<InvoiceResponse> invoices = new ArrayList<>();
        for (Integer page = lastPage; page <= lastPage; page++) {
            PaginatedInvoiceResponse invoiceResp = invoiceApi.getInvoices()
                .pageQuery(page)
                .executeAs(Response::thenReturn);
            lastPage = invoiceResp.getLastPage();
            invoices.addAll(invoiceResp.getData());
        }
        return invoices;
    }

}
