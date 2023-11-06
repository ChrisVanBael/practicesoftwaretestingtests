package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api;

import com.practicesoftwaretesting.client.model.CategoryResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;


public class DeleteCategory {

    /**
     * Deletes the Category with name
     * @param name name of the category
     * @return the task
     */
    public static Performable withName(String name) {
        return Task.where(
                actor -> {
                    Integer id = UseCategoriesApi.as(actor).getAllCategories()
                            .stream()
                            .filter(categoryResponse -> categoryResponse.getName().equals(name))
                            .map(CategoryResponse::getId)
                            .findFirst().get();
                    UseCategoriesApi.as(actor).deleteCategory(id);
                }
        );
    }
}
