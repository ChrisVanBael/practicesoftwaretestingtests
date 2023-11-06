package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.practicesoftwaretesting.client.ApiClient;
import com.practicesoftwaretesting.client.ApiException;
import com.practicesoftwaretesting.client.api.CategoryApi;
import com.practicesoftwaretesting.client.model.CategoryRequest;
import com.practicesoftwaretesting.client.model.CategoryResponse;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

import java.util.List;


public class UseCategoriesApi implements Ability {

    private String baseUrl;
    private ApiClient apiClient = new ApiClient();
    private CategoryApi categoryApi = new CategoryApi();


    private UseCategoriesApi(String baseUrl) {
        this.baseUrl = baseUrl;
        apiClient.setBasePath(baseUrl);
        categoryApi.setApiClient(apiClient);
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

    public List<CategoryResponse> getCategoryTree(String categorySlug) {
        try {
            return categoryApi.getCategoriesTree(categorySlug);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<CategoryResponse> getAllCategories() {
        try {
            return categoryApi.getCategories();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createCategory(CategoryRequest category) {
        try {
            categoryApi.storeCategory(category);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(CategoryRequest category, Integer categoryId ) {
        try {
            categoryApi.updateCategory(category, categoryId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public CategoryResponse getCategory(Integer categoryId) {
        try {
            return categoryApi.getCategory(categoryId);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCategory(Integer categoryId) {
        try {
            categoryApi.deleteCategory(categoryId);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}

