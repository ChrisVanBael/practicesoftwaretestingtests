package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.practicesoftwaretesting.client.model.CategoryResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.stream.Collectors;


public class TheCategoryNames {

    /**
     * Returns a list of category names known by the system
     * @return list of category names
     */
    public static Question<List<String>> knownByTheSystem() {
        return Question.about("the categorynames known by the system")
                .answeredBy(
                        actor -> UseCategoriesApi.as(actor).getAllCategories()
                                .stream()
                                .map(CategoryResponse::getName)
                                .collect(Collectors.toList())
                );
    }
}
