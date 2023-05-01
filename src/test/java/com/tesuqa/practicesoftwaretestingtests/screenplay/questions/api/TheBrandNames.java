package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.practicesoftwaretesting.client.model.BrandResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.stream.Collectors;


public class TheBrandNames {

    /**
     * Returns a list of brand names known by the system
     * @return list of brand names
     */
    public static Question<List<String>> knownByTheSystem() {
        return Question.about("the brandnames known by the system")
                .answeredBy(
                        actor -> UseBrandsApi.as(actor).getAllBrands()
                                .stream()
                                .map(BrandResponse::getName)
                                .collect(Collectors.toList())
                );
    }
}
