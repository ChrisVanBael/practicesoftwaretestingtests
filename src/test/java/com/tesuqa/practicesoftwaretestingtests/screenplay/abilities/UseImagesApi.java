package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.ImageApi;
import com.practicesoftwaretesting.client.v5.model.ImageResponse;
import com.tesuqa.practicesoftwaretestingtests.utilities.TestLogger;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseImagesApi implements Ability, RefreshableApi {

    private String baseUrl;
    private ImageApi imagesApi;
    private static final TestLogger logger = TestLogger.auto();


    private UseImagesApi(String baseUrl) {
        this.baseUrl = baseUrl;
        // Register with ApiClientManager to receive updates
        ApiClientManager.getInstance(baseUrl).registerDependentApi(this);
        // Get the BrandApi from the shared ApiClient
        this.imagesApi = ApiClientManager.getInstance(baseUrl).getApiClient().image();
    }

    /**
     * Ability to Use the Brands API at a specified URL
     * @param baseUrl URL to use
     * @return UseImagesApi
     */
    public static UseImagesApi at(String baseUrl) {
        return new UseImagesApi(baseUrl);
    }

    /**
     * Used to access the Actor's ability to UseImagesApi from within the Interaction classes, such as GET or PUT
     * @param actor actor to use
     * @return UseImagesAPI
     */
    public static UseImagesApi as(Actor actor) {
        return actor.abilityTo(UseImagesApi.class);
    }

    public String toString() {
        return "call the Images API at "+ baseUrl;
    }

    /**
     * Refresh the API client - call this if the token has been updated
     */
    @Override
    public void refreshApiClient(ApiClient apiClient) {
        this.imagesApi = apiClient.image();
    }

    public List<ImageResponse> getAllImages() {
        logger.info("UseImagesApi", "Retrieving images...");
        List<ImageResponse> images = imagesApi.getImages()
            .executeAs(Response::thenReturn);
        logger.info("UseImagesApi", "Found %d images", images.size());
        logger.debug("UseImagesApi", "Found images: %s", images.size());
        return images;
    }
}

