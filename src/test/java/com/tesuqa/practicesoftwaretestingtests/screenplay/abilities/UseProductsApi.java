package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.ApiClient;
import com.practicesoftwaretesting.client.ApiException;
import com.practicesoftwaretesting.client.api.ProductApi;
import com.practicesoftwaretesting.client.model.ProductRequest;
import com.practicesoftwaretesting.client.model.ProductResponse;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseProductsApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient = new ApiClient();
    private ProductApi productApi = new ProductApi();


    private UseProductsApi(String baseUrl) {
        this.baseUrl = baseUrl;
        apiClient.setBasePath(baseUrl);
        productApi.setApiClient(apiClient);
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
            return productApi.getProducts(brandId, categoryId, isRental);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createProduct(ProductRequest product) {
        try {
            productApi.storeProduct(product);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(ProductRequest product, Integer productId ) {
        try {
            productApi.updateProduct(product, productId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public ProductResponse getProduct(Integer productId) {
        try {
            return productApi.getProduct(productId);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteProduct(Integer productId) {
        try {
            productApi.deleteProduct(productId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public List<ProductResponse> getRelatedProducts(Integer productId) {
        try {
            return productApi.getRelatedProducts(productId);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

}

