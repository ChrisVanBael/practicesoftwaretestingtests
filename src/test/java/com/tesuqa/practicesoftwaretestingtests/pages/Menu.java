package com.tesuqa.practicesoftwaretestingtests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.annotations.DefaultUrl;

public class Menu extends PageObject {

    static Target HOME = Target
            .the("home")
            .locatedBy("//a[@data-test='nav-home']");

    static Target HAND_TOOLS_CATEGORIES = Target
            .the("hand tools categories")
            .locatedBy("//a[@data-test='nav-hand-tools']");

    static Target POWER_TOOLS_CATEGORIES = Target
            .the("power tools categories")
            .locatedBy("//a[@data-test='nav-power-tools']");

    static Target CONTACT = Target
            .the("contact")
            .locatedBy("//a[@data-test='nav-contact']");

}
