package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.TextContent;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class TheProductNames {

    static By PRODUCT_NAMES = By.xpath("//h5[@data-test='product-name']");

    /**
     * Returns a list of product names on the homepage
     * @return list of product names
     */
    public static Question<List<String>> onTheHomePage() {
        return Question.about("the productnames listed on the home page")
                .answeredBy(
                        actor -> BrowseTheWeb.as(actor).findAll(PRODUCT_NAMES).textContents()
                                .stream()
                                .map(entry -> entry.trim())
                                .collect(Collectors.toList())
                );
    }
}
