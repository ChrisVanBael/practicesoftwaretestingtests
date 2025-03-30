package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;


import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.BrandApi;
import com.practicesoftwaretesting.client.v5.model.BrandRequest;
import com.practicesoftwaretesting.client.v5.model.BrandResponse;
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
    private BrandApi brandApi;


    private UseBrandsApi(String baseUrl) {
        this.baseUrl = baseUrl;

        // Get the BrandApi from the shared ApiClient
        this.brandApi = ApiClientManager.getInstance(baseUrl).getApiClient().brand();
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

    /**
     * Refresh the API client - call this if the token has been updated
     */
    public UseBrandsApi refreshApiClient() {
        this.brandApi = ApiClientManager.getInstance(baseUrl).getApiClient().brand();
        return this;
    }

    public List<BrandResponse> getAllBrands() {
        return brandApi.getBrands()
            .executeAs(Response::thenReturn);
    }

    public BrandResponse createBrand(BrandRequest brand) {
        return brandApi.storeBrand()
            .body(brand)
            .executeAs(Response::thenReturn);
    }

    public void updateBrand(BrandRequest brand, String brandId ) {
        brandApi.updateBrand()
            .body(brand)
            .brandIdPath(brandId)
            .executeAs(Response::thenReturn);
    }

    public BrandResponse getBrand(String brandId) {
        return brandApi.getBrand()
            .brandIdPath(brandId)
            .executeAs(Response::thenReturn);
    }

    public void deleteBrand(String brandId) {
        brandApi.deleteBrand()
            .brandIdPath(brandId)
            .execute(Response::thenReturn);
    }

    public List<BrandResponse> searchBrand(String query) {
        return brandApi.searchBrand()
            .qQuery(query)
            .executeAs(Response::thenReturn);
    }

}

