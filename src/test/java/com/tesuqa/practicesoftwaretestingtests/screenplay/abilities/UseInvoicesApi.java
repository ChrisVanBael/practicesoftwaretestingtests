package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.ApiClient;
import com.practicesoftwaretesting.client.ApiException;
import com.practicesoftwaretesting.client.api.InvoiceApi;
import com.practicesoftwaretesting.client.model.InlineResponse2003;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

public class UseInvoicesApi implements Ability {
    private String baseUrl;
    private ApiClient apiClient = new ApiClient();
    private InvoiceApi invoiceApi = new InvoiceApi();


    private UseInvoicesApi(String baseUrl) {
        this.baseUrl = baseUrl;
        apiClient.setBasePath(baseUrl);
        invoiceApi.setApiClient(apiClient);
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
        apiClient.setAccessToken(token);
        return this;
    }

    public InlineResponse2003 getAllInvoices() {
        try {
            return invoiceApi.getInvoices(null);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

}
