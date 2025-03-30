package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web;

import com.tesuqa.practicesoftwaretestingtests.pages.HomePage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebElement;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class TheProductNames {

    /**
     * Returns a list of product names on the homepage
     * TODO: support pagination on the home page
     * @return list of product names
     */
    public static Question<List<String>> onTheHomePage() {
        return Question.about("the product names listed on all pages of the home page")
            .answeredBy(actor -> {
                List<String> allProductNames = new ArrayList<>();

                // Collect products from the first page
                allProductNames.addAll(getProductNamesFromCurrentPage(actor));

                // Check if pagination exists
                while (hasNextPage(actor)) {
                    // Click next page button
                    actor.attemptsTo(Click.on(HomePage.NEXT_PAGE_BUTTON));
                    // Wait for the page to load and collect products
                    actor.attemptsTo(WaitUntil.the(HomePage.PRODUCT_NAMES, isVisible()));
                    allProductNames.addAll(getProductNamesFromCurrentPage(actor));
                }
                return allProductNames;
            });
    }

    /**
     * Gets product names from the current page
     * @param actor the actor browsing the web
     * @return list of product names on the current page
     */
    private static List<String> getProductNamesFromCurrentPage(Actor actor) {
        List<String> productNames =  BrowseTheWeb.as(actor).findAll(HomePage.PRODUCT_NAMES)
            .textContents()
            .stream()
            .map(String::trim)
            .collect(Collectors.toList());
        return productNames;
    }

    /**
     * Checks if there is a next page available
     * @param actor the actor browsing the web
     * @return true if a next page exists and is clickable
     */
    private static boolean hasNextPage(Actor actor) {
        try {
            WebElementFacade nextButton = BrowseTheWeb.as(actor).find(HomePage.NEXT_PAGE_BUTTON);
            return nextButton.isPresent() &&
                nextButton.isEnabled() &&
                !nextButton.getAttribute("class").contains("disabled");
        } catch (Exception e) {
            return false;
        }
    }
}
