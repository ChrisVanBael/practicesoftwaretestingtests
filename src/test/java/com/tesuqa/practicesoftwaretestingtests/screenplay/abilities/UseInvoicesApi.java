package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.InvoiceApi;

import com.practicesoftwaretesting.client.v5.model.InvoiceResponse;
import com.practicesoftwaretesting.client.v5.model.PaginatedInvoiceResponse;
import com.practicesoftwaretesting.client.v5.model.PaginatedProductResponse;
import com.practicesoftwaretesting.client.v5.model.ProductResponse;
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
    private ApiClient apiClient;
    private InvoiceApi invoiceApi;


    private UseInvoicesApi(String baseUrl) {
        // Create a custom configuration with the specified base URL
        ApiClient.Config config = ApiClient.Config.apiConfig()
            .reqSpecSupplier(() -> new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setConfig(RestAssuredConfig.config()
                    .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .defaultObjectMapperType(ObjectMapperType.GSON))
                )
            );

        // Create the API client with the custom configuration
        this.apiClient = ApiClient.api(config);

        // Get the BrandApi from the client
        this.invoiceApi = apiClient.invoice();
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

    /**
     * Set the access token for API requests
     * @param token Access token
     */
    public UseInvoicesApi setAccessToken(String token) {
        // TODO: how to set the access token?
        // apiClient.setAccessToken(token);
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
