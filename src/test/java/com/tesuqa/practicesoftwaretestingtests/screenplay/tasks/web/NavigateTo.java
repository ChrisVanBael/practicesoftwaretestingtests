package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.web;

import com.tesuqa.practicesoftwaretestingtests.pages.HomePage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;

import java.util.Locale;

public class NavigateTo
{
    public static Performable theHomePage() {
        return Task.where("{0} opens the Home Page",
                Open.browserOn().the(HomePage.class)
        );
    }

    public static Performable theProductOnTheHomepage(String productName) {
        String locator = "//h5[contains(text(),'" + productName + "')]";
        return Task.where("{0} navigates on the Home Page to the product " + productName,
            Scroll.to(locator),
            Click.on(locator)
        );
    }

}
