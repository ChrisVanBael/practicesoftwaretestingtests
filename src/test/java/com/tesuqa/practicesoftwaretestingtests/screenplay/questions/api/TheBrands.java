package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.practicesoftwaretesting.client.model.BrandResponse;
import net.serenitybdd.screenplay.Question;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;

import java.util.List;

public class TheBrands {

    /**
     * Returns a list of BrandReponses known by the system
     * @return list of BrandResponses
     */
    public static Question<List<BrandResponse>> knownByTheSystem() {
        return Question.about("the brands known by the system")
                .answeredBy(
                        actor -> UseBrandsApi.as(actor).getAllBrands()
                );
    }
}
