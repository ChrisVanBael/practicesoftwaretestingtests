package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import com.tesuqa.practicesoftwaretestingtests.pages.ProductPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class TheCategory {

    /**
     * Returns the category on the product page
     * @return the category name
     */
    public static Question<String> onTheProductPage() {
        return Question.about("the category listed on the product page")
                .answeredBy(
                        actor -> BrowseTheWeb.as(actor).find(ProductPage.PRODUCT_CATEGORY).getText()
                );
    }
}
