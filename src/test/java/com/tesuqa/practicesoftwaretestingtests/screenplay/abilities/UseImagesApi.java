package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.ImageApi;
import com.practicesoftwaretesting.client.v5.model.ImageResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseImagesApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient;
    private ImageApi imagesApi;


    private UseImagesApi(String baseUrl) {
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
        this.imagesApi = apiClient.image();
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
        return imagesApi.getImages()
            .executeAs(Response::thenReturn);
    }
}

