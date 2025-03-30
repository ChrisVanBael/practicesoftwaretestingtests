package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.ProductApi;
import com.practicesoftwaretesting.client.v5.model.PaginatedProductResponse;
import com.practicesoftwaretesting.client.v5.model.ProductRequest;
import com.practicesoftwaretesting.client.v5.model.ProductResponse;
import com.practicesoftwaretesting.client.v5.model.StoreProductResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.ArrayList;
import java.util.List;


public class UseProductsApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient;
    private ProductApi productApi;


    private UseProductsApi(String baseUrl) {
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
        this.productApi = apiClient.product();
    }

    /**
     * Ability to Use the Products API at a specified URL
     * @param baseUrl URL to use
     * @return UseProductsAPI
     */
    public static UseProductsApi at(String baseUrl) {
        return new UseProductsApi(baseUrl);
    }

    /**
     * Used to access the Actor's ability to UseProductsApi from within the Interaction classes, such as GET or PUT
     * @param actor actor to use
     * @return UseProductsApi
     */
    public static UseProductsApi as(Actor actor) {
        return actor.abilityTo(UseProductsApi.class);
    }

    public String toString() {
        return "call the Products API at "+ baseUrl;
    }

    public List<ProductResponse> getAllProducts(String brandId, String categoryId, String isRental) {
        Integer lastPage = 0;
        List<ProductResponse> products = new ArrayList<>();
        for (Integer page = lastPage; page <= lastPage; page++) {
            PaginatedProductResponse productResp = productApi.getProducts()
                .byBrandQuery(brandId)
                .byCategoryQuery(categoryId)
                .isRentalQuery(isRental)
                .pageQuery(page)
                .executeAs(Response::thenReturn);
            lastPage = productResp.getLastPage();
            products.addAll(productResp.getData());
        }
        return products;
    }

    public void createProduct(ProductRequest product) {
        StoreProductResponse response = productApi.storeProduct()
            .body(product)
            .executeAs(Response::thenReturn);
        int a = 0;
    }

    public void updateProduct(ProductRequest product, String productId ) {
        productApi.updateProduct()
            .productIdPath(productId)
            .body(product)
            .executeAs(Response::thenReturn);
    }

    public ProductResponse getProduct(String productId) {
        return productApi.getProduct()
            .productIdPath(productId)
            .executeAs(Response::thenReturn);
    }

    public void deleteProduct(String productId) {
        productApi.deleteProduct()
            .productIdPath(productId)
            .execute(Response::thenReturn);
    }

    public List<ProductResponse> getRelatedProducts(String productId) {
        return productApi.getRelatedProducts()
            .productIdPath(productId)
            .executeAs(Response::thenReturn);
    }

    public List<ProductResponse> search(String query) {
        List<ProductResponse> products = new ArrayList<>();
        Integer lastPage = 0;

        for (Integer page=0; page<=lastPage; page++) {
            PaginatedProductResponse productResp = productApi.searchProduct()
                .pageQuery(query)
                .pageQuery(page)
                .executeAs(Response::thenReturn);
            lastPage = productResp.getLastPage();
            products.addAll(productResp.getData());
        }
        return products;
    }

}

