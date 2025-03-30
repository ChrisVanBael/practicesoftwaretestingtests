package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.v5.ApiClient;
import com.practicesoftwaretesting.client.v5.api.CategoryApi;
import com.practicesoftwaretesting.client.v5.model.CategoryRequest;
import com.practicesoftwaretesting.client.v5.model.CategoryResponse;
import com.practicesoftwaretesting.client.v5.model.CategoryTreeResponse;
import com.practicesoftwaretesting.client.v5.model.UpdateResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseCategoriesApi implements Ability {

    private String baseUrl;
    private CategoryApi categoryApi;


    private UseCategoriesApi(String baseUrl) {
        this.baseUrl = baseUrl;

        // Get the BrandApi from the shared ApiClient
        this.categoryApi = ApiClientManager.getInstance(baseUrl).getApiClient().category();
    }

    /**
     * Ability to Use the Categories API at a specified URL
     * @param baseUrl URL to use
     * @return UseCategoriesAPI
     */
    public static UseCategoriesApi at(String baseUrl) {
        return new UseCategoriesApi(baseUrl);
    }

    /**
     * Used to access the Actor's ability to UseCategoriesApi from within the Interaction classes, such as GET or PUT
     * @param actor actor to use
     * @return UseCategoriesAPI
     */
    public static UseCategoriesApi as(Actor actor) {
        return actor.abilityTo(UseCategoriesApi.class);
    }

    public String toString() {
        return "call the Categories API at "+ baseUrl;
    }

    /**
     * Refresh the API client - call this if the token has been updated
     */
    public UseCategoriesApi refreshApiClient() {
        this.categoryApi = ApiClientManager.getInstance(baseUrl).getApiClient().category();
        return this;
    }

    public List<CategoryTreeResponse> getAllCategoryTrees(String categorySlug) {
        return categoryApi.getCategoriesTree()
            .byCategorySlugQuery(categorySlug)
            .executeAs(Response::thenReturn);
    }


    public List<CategoryResponse> getAllCategories() {
        return categoryApi.getCategories()
            .executeAs(Response::thenReturn);
    }

    public CategoryResponse createCategory(CategoryRequest category) {
        return categoryApi.storeCategory()
            .body(category)
            .executeAs(Response::thenReturn);
    }

    public CategoryTreeResponse getCategoryTree(String categoryId) {
        return categoryApi.getCategory()
            .categoryIdPath(categoryId)
            .executeAs(Response::thenReturn);
    }

     public void updateCategory(CategoryRequest category, String categoryId ) {
        categoryApi.updateCategory()
            .categoryIdPath(categoryId)
            .body(category)
            .executeAs(Response::thenReturn);
    }

    public void deleteCategory(String categoryId) {
        categoryApi.deleteCategory()
            .categoryIdPath(categoryId)
            .execute(Response::thenReturn);
    }
}

