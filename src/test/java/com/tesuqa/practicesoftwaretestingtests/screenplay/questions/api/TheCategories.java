package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.practicesoftwaretesting.client.model.CategoryResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import net.serenitybdd.screenplay.Question;

import java.util.List;

public class TheCategories {

    /**
     * Returns a list of CategoryResponses known by the system
     * @return list of CategoryResponses
     */
    public static Question<List<CategoryResponse>> knownByTheSystem() {
        return Question.about("the categories known by the system")
                .answeredBy(
                        actor -> UseCategoriesApi.as(actor).getAllCategories()
                );
    }
}
