package com.tesuqa.practicesoftwaretestingtests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

public class CategoryPage extends PageObject {

    static Target PRODUCT_LIST = Target
            .the("product list")
            .locatedBy("(//div[@class='container'])[2]");

    /* This is an example of a Target with a dynamic attribute
     * You can then use the .of() method to instantiate the target with the value you are interested in
     * actor.attemptsTo(Click.on(PRODUCT_WITH_NAME.of('Claw Hammer')));
     * https://serenity-bdd.github.io/docs/screenplay/screenplay_webdriver#using-dynamic-targets
     */
    static Target PRODUCT_WITH_NAME = Target
            .the("product with name {0}")
            .locatedBy("//h5[contains(text(),'{0}')]/ancestor::a");
}
