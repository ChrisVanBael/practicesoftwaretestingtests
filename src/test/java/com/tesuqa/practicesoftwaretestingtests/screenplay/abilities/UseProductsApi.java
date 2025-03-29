package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.v1.client.ApiClient;
import com.practicesoftwaretesting.client.v1.api.ProductApi;
import com.practicesoftwaretesting.client.v1.model.ProductRequest;
import com.practicesoftwaretesting.client.v1.model.ProductResponse;
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

    public List<ProductResponse> getAllProducts(Integer brandId, Integer categoryId, String isRental) {
        try {
            return productApi.getProducts()
                .byBrandQuery(brandId)
                .byCategoryQuery(categoryId)
                .isRentalQuery(isRental)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            }
    }

    public void createProduct(ProductRequest product) {
        try {
            productApi.storeProduct()
                .body(product)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(ProductRequest product, Integer productId ) {
        try {
            productApi.updateProduct()
                .productIdPath(productId)
                .body(product)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProductResponse getProduct(Integer productId) {
        try {
            return productApi.getProduct()
                .productIdPath(productId)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteProduct(Integer productId) {
        try {
            productApi.deleteProduct()
                .productIdPath(productId)
                .execute(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductResponse> getRelatedProducts(Integer productId) {
        try {
            return productApi.getRelatedProducts()
                .productIdPath(productId)
                .executeAs(Response::thenReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

