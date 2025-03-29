package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;


import com.practicesoftwaretesting.v1.client.ApiClient;
import com.practicesoftwaretesting.client.v1.api.BrandApi;
import com.practicesoftwaretesting.client.v1.model.BrandRequest;
import com.practicesoftwaretesting.client.v1.model.BrandResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseBrandsApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient;
    private BrandApi brandApi;


    private UseBrandsApi(String baseUrl) {
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
        this.brandApi = apiClient.brand();
    }

    /**
     * Ability to Use the Brands API at a specified URL
     * @param baseUrl URL to use
     * @return UseBrandsAPI
     */
    public static UseBrandsApi at(String baseUrl) {
        return new UseBrandsApi(baseUrl);
    }

    /**
     * Used to access the Actor's ability to UseBrandsApi from within the Interaction classes, such as GET or PUT
     * @param actor actor to use
     * @return UseBrandsAPI
     */
    public static UseBrandsApi as(Actor actor) {
        return actor.abilityTo(UseBrandsApi.class);
    }

    public String toString() {
        return "call the Brands API at "+ baseUrl;
    }

    public List<BrandResponse> getAllBrands() {
        try {
            return brandApi.getBrands()
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BrandResponse createBrand(BrandRequest brand) {
        try {
            brandApi.storeBrand()
                .body(brand)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateBrand(BrandRequest brand, String brandId ) {
        try {
            brandApi.updateBrand()
                .body(brand)
                .brandIdPath(brandId)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BrandResponse getBrand(String brandId) {
        try {
            return brandApi.getBrand()
                .brandIdPath(brandId)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteBrand(String brandId) {
        try {
            brandApi.deleteBrand()
                .brandIdPath(brandId)
                .execute(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BrandResponse> searchBrand(String query) {
        try {
            return brandApi.searchBrand(query);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

}

