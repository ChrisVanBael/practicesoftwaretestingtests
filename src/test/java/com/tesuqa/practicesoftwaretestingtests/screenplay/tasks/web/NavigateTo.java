package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.web;

import com.tesuqa.practicesoftwaretestingtests.pages.HomePage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import java.util.Locale;

public class NavigateTo
{
    public static Performable theHomePage() {
        return Task.where("{0} opens the Home Page",
                Open.browserOn().the(HomePage.class)
        );
    }

}
