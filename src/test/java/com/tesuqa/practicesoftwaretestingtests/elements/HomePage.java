package com.tesuqa.practicesoftwaretestingtests.elements;

import org.openqa.selenium.By;
import net.serenitybdd.screenplay.targets.Target;

public class HomePage {

    /*
     * the @DefaultUrl attribute allows you to open the page directly
     * by using actor.attemptsTo(Open.browserOn().the(HomePage.class));
     * https://serenity-bdd.github.io/docs/screenplay/screenplay_webdriver#opening-the-url-of-a-page-object
     */

    /*
     * This is standard Target by XPath, we select the second matching element
     */
    static Target PRODUCT_LIST = Target
            .the("product list")
            .locatedBy("(//div[@class='container'])[2]");


    /*
     * This is a standard Target by XPath
     * https://serenity-bdd.github.io/docs/screenplay/screenplay_webdriver#using-the-target-class
     */
    static Target PRODUCT1 = Target
            .the("product1")
            .locatedBy("//p[@data-test='product-1']");


    static Target PRODUCT2 = Target
            .the("product2")
            .locatedBy("//p[@data-test='product-2']");

    /* In v1 there is no sorting yet on the HomePage
     * In later versions this will be added
     * Then the first product in the list will not always match @data-test='product-1'  !!!
     */


    static Target PRODUCTNAMES = Target
            .the("productnames")
            .locatedBy("//h5[@data-test='product-name']/text()");

    static By PRODUCT_NAMES = By.xpath("//h5[@data-test='product-name']/text()");
}
