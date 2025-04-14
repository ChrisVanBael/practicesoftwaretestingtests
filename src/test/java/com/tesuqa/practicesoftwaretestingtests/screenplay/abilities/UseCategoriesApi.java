package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.CategoryApi;
import com.practicesoftwaretesting.client.v5.model.CategoryRequest;
import com.practicesoftwaretesting.client.v5.model.CategoryResponse;
import com.practicesoftwaretesting.client.v5.model.CategoryTreeResponse;
import com.practicesoftwaretesting.client.v5.model.UpdateResponse;
import net.serenitybdd.screenplay.Actor;

import java.util.List;
import java.util.Objects;

/**
 * Ability to interact with the Categories API
 */
public class UseCategoriesApi extends BaseApiAbility {

    private CategoryApi categoryApi;

    private UseCategoriesApi(String baseUrl) {
        super(baseUrl);
        this.categoryApi = ApiClientManager.getInstance(baseUrl).getApiClient().category();
    }

    public static UseCategoriesApi at(String baseUrl) {
        return new UseCategoriesApi(baseUrl);
    }

    public static UseCategoriesApi as(Actor actor) {
        return actor.abilityTo(UseCategoriesApi.class);
    }

    @Override
    public String toString() {
        return "call the Categories API at " + baseUrl;
    }

    @Override
    public void refreshApiClient(ApiClient apiClient) {
        this.categoryApi = apiClient.category();
    }

    public List<CategoryTreeResponse> getAllCategoryTrees(String categorySlug) {
        Objects.requireNonNull(categorySlug, "Category slug cannot be null");

        List<CategoryTreeResponse> categoryTrees = executeApiCallForList(
            "retrieve all category trees",
            () -> categoryApi.getCategoriesTree().byCategorySlugQuery(categorySlug).execute(r -> r),
            CategoryTreeResponse.class
        );
        return categoryTrees;
    }

    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categories = executeApiCallForList(
            "retrieve all categories",
            () -> categoryApi.getCategories().execute(r -> r),
            CategoryResponse.class
        );
        return categories;
    }

    public CategoryResponse createCategory(CategoryRequest category) {
        Objects.requireNonNull(category, "Category request cannot be null");

        CategoryResponse created = executeApiCall(
            "create category",
            () -> categoryApi.storeCategory().body(category).execute(r -> r),
            CategoryResponse.class
        );
        return created;
    }

    public CategoryTreeResponse getCategoryTree(String categoryId) {
        Objects.requireNonNull(categoryId, "Category ID cannot be null");

        CategoryTreeResponse categoryTree = executeApiCall(
            "get category tree with ID " + categoryId,
            () -> categoryApi.getCategory().categoryIdPath(categoryId).execute(r -> r),
            CategoryTreeResponse.class
        );
        return categoryTree;
    }

    public UpdateResponse updateCategory(CategoryRequest category, String categoryId) {
        Objects.requireNonNull(category, "Category request cannot be null");
        Objects.requireNonNull(categoryId, "Category ID cannot be null");

        UpdateResponse response = executeApiCall(
            "update category with ID " + categoryId,
            () -> categoryApi.updateCategory().categoryIdPath(categoryId).body(category).execute(r -> r),
            UpdateResponse.class
        );
        return response;
    }

    public void deleteCategory(String categoryId) {
        Objects.requireNonNull(categoryId, "Category ID cannot be null");

        executeApiCall(
            "delete category with ID " + categoryId,
            () -> categoryApi.deleteCategory().categoryIdPath(categoryId).execute(r -> r),
            Object.class
        );
    }

    public List<CategoryResponse> searchCategory(String query) {
        Objects.requireNonNull(query, "Search query cannot be null");

        List<CategoryResponse> categories = executeApiCallForList(
            "search categories with query '" + query + "'",
            () -> categoryApi.searchCategory().qQuery(query).execute(r -> r),
            CategoryResponse.class
        );
        return categories;
    }
}
