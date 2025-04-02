package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.RefreshableApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import java.util.ArrayList;
import java.util.List;

public class ApiClientManager implements Ability {
    private static ApiClientManager instance;
    private ApiClient apiClient;
    private String baseUrl;
    private String accessToken;
    private final List<RefreshableApi> dependentApis = new ArrayList<>();

    private ApiClientManager(String baseUrl) {
        this.baseUrl = baseUrl;
        updateApiClient();
    }

    public static synchronized ApiClientManager getInstance(String baseUrl) {
        if (instance == null) {
            instance = new ApiClientManager(baseUrl);
        }
        return instance;
    }

    public static ApiClientManager as(Actor actor) {
        return actor.abilityTo(ApiClientManager.class);
    }

    public static Ability withBaseUrl(String baseUrl) {
        return getInstance(baseUrl);
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public ApiClientManager setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        updateApiClient();
        notifyDependentApis();
        return this;
    }

    public void registerDependentApi(RefreshableApi api) {
        dependentApis.add(api);
    }

    private void updateApiClient() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri(baseUrl)
            .setConfig(RestAssuredConfig.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                    .defaultObjectMapperType(ObjectMapperType.GSON))
            );

        // Add authorization header if token exists
        if (accessToken != null && !accessToken.isEmpty()) {
            requestSpecBuilder.addHeader("Authorization", "Bearer " + accessToken);
        }

        ApiClient.Config config = ApiClient.Config.apiConfig()
            .reqSpecSupplier(() -> requestSpecBuilder);

        this.apiClient = ApiClient.api(config);
    }

    private void notifyDependentApis() {
        for (RefreshableApi api : dependentApis) {
            api.refreshApiClient(apiClient);
        }
    }
}
