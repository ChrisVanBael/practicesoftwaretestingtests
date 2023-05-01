package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.ApiClient;
import com.practicesoftwaretesting.client.ApiException;
import com.practicesoftwaretesting.client.api.BrandApi;

import com.practicesoftwaretesting.client.model.BrandRequest;
import com.practicesoftwaretesting.client.model.BrandResponse;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseBrandsApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient = new ApiClient();
    private BrandApi brandApi = new BrandApi();


    private UseBrandsApi(String baseUrl) {
        this.baseUrl = baseUrl;
        apiClient.setBasePath(baseUrl);
        brandApi.setApiClient(apiClient);
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
            return brandApi.getBrands();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createBrand(BrandRequest brand) {
        try {
            brandApi.storeBrand(brand);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void updateBrand(BrandRequest brand, Integer brandId ) {
        try {
            brandApi.updateBrand(brand, brandId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public BrandResponse getBrand(Integer brandId) {
        try {
            return brandApi.getBrand(brandId);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteBrand(Integer brandId) {
        try {
            brandApi.deleteBrand(brandId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}

