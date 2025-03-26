package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.ApiClient;
import com.practicesoftwaretesting.client.ApiException;
import com.practicesoftwaretesting.client.api.ProductApi;
import com.practicesoftwaretesting.client.model.PaginatedProductResponse;
import com.practicesoftwaretesting.client.model.ProductRequest;
import com.practicesoftwaretesting.client.model.ProductResponse;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.ArrayList;
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

    public List<ProductResponse> getAllProducts(String brandId, String categoryId, String isRental, String priceRange, String sort) {
        List<ProductResponse> products = new ArrayList<>();
        Integer lastPage = 0;

        for (Integer page=0; page<=lastPage; page++) {
            try {
                PaginatedProductResponse productResp = productApi.getProducts(brandId, categoryId, isRental, priceRange, sort, page);
                lastPage = productResp.getLastPage();
                products.addAll(productResp.getData());
            } catch (ApiException e) {
                e.printStackTrace();
                return null;
            }
        }
        return products;
    }

    public void createProduct(ProductRequest product) {
        try {
            productApi.storeProduct(product);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(ProductRequest product, String productId ) {
        try {
            productApi.updateProduct(productId, product);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public ProductResponse getProduct(String productId) {
        try {
            return productApi.getProduct(productId);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteProduct(String productId) {
        try {
            productApi.deleteProduct(productId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public List<ProductResponse> getRelatedProducts(String productId) {
        try {
            return productApi.getRelatedProducts(productId);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductResponse> search(String query) {
        List<ProductResponse> products = new ArrayList<>();
        Integer lastPage = 0;

        for (Integer page=0; page<=lastPage; page++) {
            try {
                PaginatedProductResponse productResp = productApi.searchProduct(query, page);
                lastPage = productResp.getLastPage();
                products.addAll(productResp.getData());
            } catch (ApiException e) {
                e.printStackTrace();
                return null;
            }
        }
        return products;
    }

}

