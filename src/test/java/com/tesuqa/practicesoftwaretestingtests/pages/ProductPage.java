package com.tesuqa.practicesoftwaretestingtests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;


public class ProductPage extends PageObject {

    static Target PRODUCT_NAME = Target
            .the("product name")
            .locatedBy("//h1[@data-test='product-name']");

    static Target UNIT_PRICE = Target
            .the("unit price")
            .locatedBy("//span[@data-test='unit-price']");

    static Target PRODUCT_DESCRIPTION = Target
            .the("product description")
            .locatedBy("//p[@data-test='product-description']");

}
