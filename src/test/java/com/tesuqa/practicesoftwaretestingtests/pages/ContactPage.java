package com.tesuqa.practicesoftwaretestingtests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://v1.practicesoftwaretesting.com/#/contact")
public class ContactPage extends PageObject {

    /* For interacting with the different types of elements, see
     * https://serenity-bdd.github.io/docs/screenplay/screenplay_webdriver#interacting-with-elements
     */

    /* Usage:
     * actor.attemptsTo(Clear.field(FIRST_NAME));
     * actor.attemptsTo(Enter.theValue("Chris").into(FIRST_NAME));
     */
    static Target FIRST_NAME = Target
            .the("first name")
            .locatedBy("//input[@data-test='first-name']");

    static Target LAST_NAME = Target
            .the("last name")
            .locatedBy("//input[@data-test='last-name']");

    static Target EMAIL = Target
            .the("email")
            .locatedBy("//input[@data-test='email']");

    /* Usage:
     * actor.attemptsTo(SelectFromOptions.byVisibleText("Warranty").from(SUBJECT));
     */
    static Target SUBJECT = Target
            .the("subject")
            .locatedBy("//select[@data-test='subject']");

    static Target MESSAGE = Target
            .the("message")
            .locatedBy("//textarea[@data-test='message']");

    /* Usage:
     * actor.attemptsTo(Click.on(SEND));
     * and since it is at the bottom of the screen, maybe you'll want to
     * actor.attemptsTo(Scroll.to(SEND));
     */
    static Target SEND = Target
            .the("message")
            .locatedBy("//input[@data-test='contact-submit']");
}
