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
    private ApiClient apiClient;
    private UserApi userApi;


    private UseUsersApi(String baseUrl) {
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

        // Get the UsersApi from the client
        this.userApi = apiClient.user();
    }

    /**
     * Used to access the Actor's ability to UseUsersApi from within the Interaction classes, such as GET or PUT
     * @param actor actor to use
     * @return UseUsersApi
     */
    public static UseUsersApi as(Actor actor) {
        return actor.abilityTo(UseUsersApi.class);
    }

    /**
     * Ability to Use the Products API at a specified URL
     * @param baseUrl URL to use
     * @return UseUsersApi
     */
    public static UseUsersApi at(String baseUrl) {
        return new UseUsersApi(baseUrl);
    }

    public String login(AccountRequest login) {
        TokenResponse response = userApi.loginCustomer()
            .body(login)
            .executeAs(Response::thenReturn);
        return response.getAccessToken();
    }
}
