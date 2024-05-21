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

    /**
     * Returns a list of Brands where the name partially or fully matches the query
     * @param query part of the name of the Brand
     * @return list of BrandResponses
     */
    public static Question<List<BrandResponse>> foundWithQuery(String query) {
        return Question.about("the brands found with query: " +  query)
            .answeredBy(
                actor -> UseBrandsApi.as(actor).searchBrand(query)
            );
    }
}
