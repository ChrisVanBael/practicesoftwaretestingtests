package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.UserApi;
import com.practicesoftwaretesting.client.v5.model.AccountRequest;
import com.practicesoftwaretesting.client.v5.model.TokenResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

public class UseUsersApi implements Ability {

    private String baseUrl;
    private UserApi userApi;


    private UseUsersApi(String baseUrl) {
        this.baseUrl = baseUrl;

        // Initialize the ApiClientManager with the base URL if not already initialized
        this.userApi = ApiClientManager.getInstance(baseUrl).getApiClient().user();
    }

    /**
     * Ability to Use the Users API at a specified URL
     * @param baseUrl URL to use
     * @return UseProductsAPI
     */
    public static UseUsersApi at(String baseUrl) {
        return new UseUsersApi(baseUrl);
    }

    /**
     * Used to access the Actor's ability to UseProductsApi from within the Interaction classes, such as GET or PUT
     * @param actor actor to use
     * @return UseProductsApi
     */
    public static UseUsersApi as(Actor actor) {
        return actor.abilityTo(UseUsersApi.class);
    }

    public String toString() {
        return "call the Users API at "+ baseUrl;
    }

    /**
     * Refresh the API client - call this if the token has been updated
     */
    public UseUsersApi refreshApiClient() {
        this.userApi = ApiClientManager.getInstance(baseUrl).getApiClient().user();
        return this;
    }
    public String login(AccountRequest login) {
        TokenResponse response = userApi.loginCustomer()
            .body(login)
            .executeAs(Response::thenReturn);
        return response.getAccessToken();
    }
}
