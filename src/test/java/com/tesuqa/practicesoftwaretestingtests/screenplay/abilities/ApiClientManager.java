package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.tesuqa.practicesoftwaretestingtests.utilities.TestLogger;
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
    private static final TestLogger logger = TestLogger.auto();

    private ApiClientManager(String baseUrl) {
        this.baseUrl = baseUrl;
        updateApiClient();
    }

    public static synchronized ApiClientManager getInstance(String baseUrl) {
        if (instance == null) {
            logger.info("ApiClientManager Instance", "Creating singleton instance of ApiClientManager");
            instance = new ApiClientManager(baseUrl);
        } else {
            logger.debug("ApiClientManager Instance", "Returning existing ApiClientManager instance");
        }
        return instance;
    }

    public static ApiClientManager as(Actor actor) {
        logger.debug("Actor Ability", "Retrieved ApiClientManager ability for actor: " + actor.getName());
        return actor.abilityTo(ApiClientManager.class);
    }

    public static Ability withBaseUrl(String baseUrl) {
        logger.info("Ability Creation", "Creating ApiClientManager ability with base URL: " + baseUrl);
        return getInstance(baseUrl);
    }

    public ApiClient getApiClient() {
        logger.debug("API Client", "Getting API client instance");
        return apiClient;
    }

    public ApiClientManager setAccessToken(String accessToken) {
        logger.info("Access Token", "Setting new access token for API client");
        if (accessToken == null || accessToken.isEmpty()) {
            logger.warn("Access Token", "Empty or null access token provided");
        }
        this.accessToken = accessToken;
        updateApiClient();
        notifyDependentApis();
        return this;
    }

    public void registerDependentApi(RefreshableApi api) {
        logger.info("API Registration", "Registering dependent API: " + api.getClass().getSimpleName());
        dependentApis.add(api);
        logger.debug("API Registration", "Total registered dependent APIs: " + dependentApis.size());
    }

    private void updateApiClient() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri(baseUrl)
            .setConfig(RestAssuredConfig.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                    .defaultObjectMapperType(ObjectMapperType.GSON)));

        // Add authorization header if token exists
        if (accessToken != null && !accessToken.isEmpty()) {
            logger.debug("Authorization", "Adding Bearer token to request header");
            requestSpecBuilder.addHeader("Authorization", "Bearer " + accessToken);
        }

        ApiClient.Config config = ApiClient.Config.apiConfig()
            .reqSpecSupplier(() -> createFreshRequestSpec());

        this.apiClient = ApiClient.api(config);
        logger.info("API Client Update", "Successfully updated API client configuration");
    }

    /**
     * Creates a fresh request specification for each API call to prevent
     * request bodies from persisting between requests
     *
     * @return A new RequestSpecBuilder with the current configuration
     */
    public RequestSpecBuilder createFreshRequestSpec() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri(baseUrl)
            .setConfig(RestAssuredConfig.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                    .defaultObjectMapperType(ObjectMapperType.GSON)));

        // Add authorization header if token exists
        if (accessToken != null && !accessToken.isEmpty()) {
            requestSpecBuilder.addHeader("Authorization", "Bearer " + accessToken);
        }

        return requestSpecBuilder;
    }

    private void notifyDependentApis() {
        for (RefreshableApi api : dependentApis) {
            logger.debug("API Refresh", "Refreshing API client for: " + api.getClass().getSimpleName());
            api.refreshApiClient(apiClient);
        }
    }
}
