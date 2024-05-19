package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import com.tesuqa.practicesoftwaretestingtests.pages.ProductPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class ThePrice {

    /**
     * Returns the price on the product page
     * @return the rice name
     */
    public static Question<String> onTheProductPage() {
        return Question.about("the price listed on the product page")
                .answeredBy(
                        actor -> BrowseTheWeb.as(actor).find(ProductPage.PRODUCT_PRICE).getText()
                );
    }
}
