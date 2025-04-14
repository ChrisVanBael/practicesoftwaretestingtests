package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.BrandApi;
import com.practicesoftwaretesting.client.v5.model.BrandRequest;
import com.practicesoftwaretesting.client.v5.model.BrandResponse;
import com.practicesoftwaretesting.client.v5.model.UpdateResponse;
import net.serenitybdd.screenplay.Actor;

import java.util.List;
import java.util.Objects;

/**
 * Ability to interact with the Brands API
 */
public class UseBrandsApi extends BaseApiAbility {

    private BrandApi brandApi;

    private UseBrandsApi(String baseUrl) {
        super(baseUrl);
        this.brandApi = ApiClientManager.getInstance(baseUrl).getApiClient().brand();
    }

    public static UseBrandsApi at(String baseUrl) {
        return new UseBrandsApi(baseUrl);
    }

    public static UseBrandsApi as(Actor actor) {
        return actor.abilityTo(UseBrandsApi.class);
    }

    @Override
    public void refreshApiClient(ApiClient apiClient) {
        this.brandApi = apiClient.brand();
    }

    public List<BrandResponse> getAllBrands() {
        List<BrandResponse> brands = executeApiCallForList(
            "retrieve all brands",
            () -> brandApi.getBrands().execute(r -> r),
            BrandResponse.class
        );
        return brands;
    }

    public BrandResponse createBrand(BrandRequest brand) {
        Objects.requireNonNull(brand, "Brand request cannot be null");

        BrandResponse created = executeApiCall(
            "create brand",
            () -> brandApi.storeBrand().body(brand).execute(r -> r),
            BrandResponse.class
        );
        return created;
    }

    public UpdateResponse updateBrand(BrandRequest brand, String brandId) {
        Objects.requireNonNull(brand, "Brand request cannot be null");
        Objects.requireNonNull(brandId, "Brand ID cannot be null");

        UpdateResponse response = executeApiCall(
            "update brand with ID " + brandId,
            () -> brandApi.updateBrand().body(brand).brandIdPath(brandId).execute(r -> r),
            UpdateResponse.class
        );
        return response;
    }

    public BrandResponse getBrand(String brandId) {
        Objects.requireNonNull(brandId, "Brand ID cannot be null");

        BrandResponse brand = executeApiCall(
            "get brand with ID " + brandId,
            () -> brandApi.getBrand().brandIdPath(brandId).execute(r -> r),
            BrandResponse.class
        );
        return brand;
    }

    public void deleteBrand(String brandId) {
        Objects.requireNonNull(brandId, "Brand ID cannot be null");

        executeApiCall(
            "delete brand with ID " + brandId,
            () -> brandApi.deleteBrand().brandIdPath(brandId).execute(r -> r),
            Object.class
        );
    }

    public List<BrandResponse> searchBrand(String query) {
        Objects.requireNonNull(query, "Search query cannot be null");

        List<BrandResponse> brands = executeApiCallForList(
            "search brands with query '" + query + "'",
            () -> brandApi.searchBrand().qQuery(query).execute(r -> r),
            BrandResponse.class
        );
        return brands;
    }
}
