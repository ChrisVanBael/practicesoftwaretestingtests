package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api;

import com.practicesoftwaretesting.client.model.CategoryRequest;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;


public class AddCategory {

    /**
     * Adds the Category with name
     * @param name name of the category
     * @return the task
     */
    public static Performable withNameAndSlug(String name, String slug) {
        return Task.where(
                actor -> {
                    CategoryRequest newCategory = new CategoryRequest();
                    newCategory.setName(name);
                    newCategory.setSlug(slug);
                    UseCategoriesApi.as(actor).createCategory(newCategory);
                }
        );
    }
}
