package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.ApiClient;
import com.practicesoftwaretesting.client.ApiException;
import com.practicesoftwaretesting.client.api.UserApi;
import com.practicesoftwaretesting.client.model.UsersLoginBody;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

public class UseUsersApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient = new ApiClient();
    private UserApi userApi = new UserApi();


    private UseUsersApi(String baseUrl) {
        this.baseUrl = baseUrl;
        apiClient.setBasePath(baseUrl);
        userApi.setApiClient(apiClient);
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

    public String login(UsersLoginBody login) {
        try {
            return userApi.loginCustomer(login).getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
