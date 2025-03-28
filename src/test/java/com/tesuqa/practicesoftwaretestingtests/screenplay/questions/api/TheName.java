package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import net.serenitybdd.screenplay.Question;

import java.util.stream.Collectors;


public class TheName {

    /**
     * Searches for the name of a brand
     * @return name of the brand
     */
    public static Question<String> ofBrand(Integer id) {
        return Question.about("the name of brand with id " + id)
                .answeredBy(
                        actor -> UseBrandsApi.as(actor).getAllBrands()
                                .stream()
                                .filter(brand -> brand.getId().equals(id))
                                .collect(Collectors.toList())
                                .get(0)
                                .getName()
                );
    }


    /**
     * Searches for the name of a category
     * @return name of the category
     */
    public static Question<String> ofCategory(Integer id) {

        return Question.about("the name of category with id " + id)
                .answeredBy(
                        actor -> UseCategoriesApi.as(actor).getAllCategories()
                                .stream()
                                .filter(category -> category.getId().equals(id))
                                .collect(Collectors.toList())
                                .get(0)
                                .getName()
                );
    }
}
