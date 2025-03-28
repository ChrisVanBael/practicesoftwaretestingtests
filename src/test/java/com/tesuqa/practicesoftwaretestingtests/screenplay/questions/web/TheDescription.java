package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import com.tesuqa.practicesoftwaretestingtests.pages.ProductPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class TheDescription {

    /**
     * Returns the description on the product page
     * @return the description
     */
    public static Question<String> onTheProductPage() {
        return Question.about("the description listed on the product page")
                .answeredBy(
                        actor -> BrowseTheWeb.as(actor).find(ProductPage.PRODUCT_DESCRIPTION).getText()
                );
    }
}
