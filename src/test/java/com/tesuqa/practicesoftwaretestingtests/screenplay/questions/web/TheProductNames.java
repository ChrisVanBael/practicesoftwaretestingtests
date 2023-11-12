package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.TextContent;
import org.openqa.selenium.By;

import java.util.List;

public class TheProductNames {

    static By PRODUCT_NAMES = By.xpath("//h5[@data-test='product-name']/text()");

    /**
     * Returns a list of product names on the homepage
     * @return list of product names
     */
    public static Question<List<String>> onTheHomePage() {
        return Question.about("the productnames listed on the home page")
                .answeredBy(
                        actor -> (List<String>) TextContent.of(PRODUCT_NAMES)
                );
    }
}
