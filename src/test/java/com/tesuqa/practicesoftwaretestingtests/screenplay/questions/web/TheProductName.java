package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import com.tesuqa.practicesoftwaretestingtests.pages.ProductPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import java.util.List;
import java.util.stream.Collectors;

public class TheProductName {

    /**
     * Returns the product name on the product page
     * @return the product name
     */
    public static Question<String> onTheProductPage() {
        return Question.about("the product name listed on the product page")
                .answeredBy(
                        actor -> BrowseTheWeb.as(actor).find(ProductPage.PRODUCT_NAME).getText()
                );
    }
}
