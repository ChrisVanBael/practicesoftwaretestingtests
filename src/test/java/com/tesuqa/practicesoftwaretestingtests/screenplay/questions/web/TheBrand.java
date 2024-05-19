package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import com.tesuqa.practicesoftwaretestingtests.pages.ProductPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class TheBrand {

    /**
     * Returns the brand name on the product page
     * @return the brand name
     */
    public static Question<String> onTheProductPage() {
        return Question.about("the brand name listed on the product page")
                .answeredBy(
                        actor -> BrowseTheWeb.as(actor).find(ProductPage.PRODUCT_BRAND).getText()
                );
    }
}
