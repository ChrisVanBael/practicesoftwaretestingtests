package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.ProductApi;
import com.practicesoftwaretesting.client.v5.model.PaginatedProductResponse;
import com.practicesoftwaretesting.client.v5.model.ProductRequest;
import com.practicesoftwaretesting.client.v5.model.ProductResponse;
import com.practicesoftwaretesting.client.v5.model.UpdateResponse;
import net.serenitybdd.screenplay.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Ability to interact with the Products API
 */
public class UseProductsApi extends BaseApiAbility {

    private ProductApi productApi;

    private UseProductsApi(String baseUrl) {
        super(baseUrl);
        this.productApi = ApiClientManager.getInstance(baseUrl).getApiClient().product();
    }

    public static UseProductsApi at(String baseUrl) {
        return new UseProductsApi(baseUrl);
    }

    public static UseProductsApi as(Actor actor) {
        return actor.abilityTo(UseProductsApi.class);
    }

    @Override
    public String toString() {
        return "call the Products API at " + baseUrl;
    }

    @Override
    public void refreshApiClient(ApiClient apiClient) {
        this.productApi = apiClient.product();
    }

    /**
     * Retrieves all products, optionally filtered by brand, category, and rental status
     *
     * @param brandId ID of the brand to filter by (optional)
     * @param categoryId ID of the category to filter by (optional)
     * @param isRental Filter for rental products (optional)
     * @return A list of product responses
     */
    public List<ProductResponse> getAllProducts(String brandId, String categoryId, String isRental) {
        List<ProductResponse> allProducts = new ArrayList<>();
        Integer currentPage = 1;
        Integer lastPage = 1;

        do {
            Integer finalCurrentPage = currentPage;
            PaginatedProductResponse paginatedResponse = executeApiCall(
                "retrieve products page " + currentPage,
                () -> productApi.getProducts()
                    .byBrandQuery(brandId)
                    .byCategoryQuery(categoryId)
                    .isRentalQuery(isRental)
                    .pageQuery(finalCurrentPage)
                    .execute(r -> r),
                PaginatedProductResponse.class
            );

            allProducts.addAll(paginatedResponse.getData());
            lastPage = paginatedResponse.getLastPage();
            currentPage++;
        } while (currentPage <= lastPage);

        return allProducts;
    }

    /**
     * Creates a new product
     *
     * @param product The product request data
     * @return The created product response
     */
    public ProductResponse createProduct(ProductRequest product) {
        Objects.requireNonNull(product, "Product request cannot be null");

        ProductResponse created = executeApiCall(
            "create product",
            () -> productApi.storeProduct().body(product).execute(r -> r),
            ProductResponse.class
        );
        return created;
    }

    /**
     * Updates an existing product
     *
     * @param product The updated product data
     * @param productId The ID of the product to update
     * @return The update response
     */
    public UpdateResponse updateProduct(ProductRequest product, String productId) {
        Objects.requireNonNull(product, "Product request cannot be null");
        Objects.requireNonNull(productId, "Product ID cannot be null");

        UpdateResponse response = executeApiCall(
            "update product with ID " + productId,
            () -> productApi.updateProduct().body(product).productIdPath(productId).execute(r -> r),
            UpdateResponse.class
        );
        return response;
    }

    /**
     * Retrieves a specific product by ID
     *
     * @param productId The ID of the product to retrieve
     * @return The product response
     */
    public ProductResponse getProduct(String productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");

        ProductResponse product = executeApiCall(
            "get product with ID " + productId,
            () -> productApi.getProduct().productIdPath(productId).execute(r -> r),
            ProductResponse.class
        );
        return product;
    }

    /**
     * Deletes a specific product by ID
     *
     * @param productId The ID of the product to delete
     */
    public void deleteProduct(String productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");

        executeApiCall(
            "delete product with ID " + productId,
            () -> productApi.deleteProduct().productIdPath(productId).execute(r -> r),
            Object.class
        );
    }

    /**
     * Retrieves products related to a specific product
     *
     * @param productId The ID of the product to find related products for
     * @return A list of related product responses
     */
    public List<ProductResponse> getRelatedProducts(String productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");

        List<ProductResponse> relatedProducts = executeApiCallForList(
            "get related products for product with ID " + productId,
            () -> productApi.getRelatedProducts().productIdPath(productId).execute(r -> r),
            ProductResponse.class
        );
        return relatedProducts;
    }

    /**
     * Searches for products matching a query
     *
     * @param query The search query
     * @return A list of matching product responses
     */
    public List<ProductResponse> search(String query) {
        Objects.requireNonNull(query, "Search query cannot be null");

        List<ProductResponse> allProducts = new ArrayList<>();
        Integer currentPage = 1;
        Integer lastPage = 1;

        do {
            Integer finalCurrentPage = currentPage;
            PaginatedProductResponse paginatedResponse = executeApiCall(
                "search products with query '" + query + "' page " + currentPage,
                () -> productApi.searchProduct().qQuery(query).pageQuery(finalCurrentPage).execute(r -> r),
                PaginatedProductResponse.class
            );

            allProducts.addAll(paginatedResponse.getData());
            lastPage = paginatedResponse.getLastPage();
            currentPage++;
        } while (currentPage <= lastPage);

        return allProducts;
    }

    /**
     * Partially updates an existing product
     *
     * @param product The partial product data
     * @param productId The ID of the product to update
     * @return The update response
     */
    public UpdateResponse patchProduct(ProductRequest product, String productId) {
        Objects.requireNonNull(product, "Product request cannot be null");
        Objects.requireNonNull(productId, "Product ID cannot be null");

        UpdateResponse response = executeApiCall(
            "partially update product with ID " + productId,
            () -> productApi.patchProduct().body(product).productIdPath(productId).execute(r -> r),
            UpdateResponse.class
        );
        return response;
    }

    /**
     * Retrieves a specific page of products
     *
     * @param page The page number to retrieve
     * @param brandId ID of the brand to filter by (optional)
     * @param categoryId ID of the category to filter by (optional)
     * @param isRental Filter for rental products (optional)
     * @return A paginated product response
     */
    public PaginatedProductResponse getProductsPage(Integer page, String brandId, String categoryId, String isRental) {
        Objects.requireNonNull(page, "Page number cannot be null");

        return executeApiCall(
            "retrieve products page " + page,
            () -> productApi.getProducts()
                .byBrandQuery(brandId)
                .byCategoryQuery(categoryId)
                .isRentalQuery(isRental)
                .pageQuery(page)
                .execute(r -> r),
            PaginatedProductResponse.class
        );
    }
}
