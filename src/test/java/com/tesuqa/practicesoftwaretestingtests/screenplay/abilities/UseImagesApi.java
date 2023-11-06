package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.ApiClient;
import com.practicesoftwaretesting.client.ApiException;
import com.practicesoftwaretesting.client.api.ImageApi;
import com.practicesoftwaretesting.client.model.ImageResponse;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseImagesApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient = new ApiClient();
    private ImageApi imagesApi = new ImageApi();


    private UseImagesApi(String baseUrl) {
        this.baseUrl = baseUrl;
        apiClient.setBasePath(baseUrl);
        imagesApi.setApiClient(apiClient);
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

    public List<ImageResponse> getAllImages() {
        try {
            return imagesApi.getImages();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}

