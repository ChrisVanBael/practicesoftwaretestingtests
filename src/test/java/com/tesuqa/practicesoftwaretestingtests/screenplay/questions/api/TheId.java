package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import net.serenitybdd.screenplay.Question;

import java.util.stream.Collectors;


public class TheId {

    /**
     * Searches for the id of a brand name
     * @return id of the brand name
     */
    public static Question<Integer> ofBrand(String brandName) {
        return Question.about("the id of brand")
                .answeredBy(
                        actor -> UseBrandsApi.as(actor).getAllBrands()
                                .stream()
                                .filter(brand -> brand.getName().contains(brandName))
                                .collect(Collectors.toList())
                                .get(0)
                                .getId()
                );
    }


    /**
     * Searches for the id of a category name
     * @return id of the category name
     */
    public static Question<Integer> ofCategory(String categoryName) {

        return Question.about("the id of category")
                .answeredBy(
                        actor -> UseCategoriesApi.as(actor).getAllCategories()
                                .stream()
                                .filter(category -> category.getName().contains(categoryName))
                                .collect(Collectors.toList())
                                .get(0)
                                .getId()
                );
    }
}
