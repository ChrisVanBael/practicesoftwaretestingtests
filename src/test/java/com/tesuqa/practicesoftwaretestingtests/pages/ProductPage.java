package com.tesuqa.practicesoftwaretestingtests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;


public class ProductPage extends PageObject {

    public static Target PRODUCT_NAME = Target
            .the("product name")
            .locatedBy("//h1[@data-test='product-name']");

    public static Target PRODUCT_PRICE = Target
            .the("unit price")
            .locatedBy("//span[@data-test='unit-price']");

    public static Target PRODUCT_DESCRIPTION = Target
            .the("product description")
            .locatedBy("//p[@data-test='product-description']");

    public static Target PRODUCT_CATEGORY = Target
        .the("product category")
        .locatedBy("//span[@aria-label='category']");

    public static Target PRODUCT_BRAND = Target
        .the("product brand")
        .locatedBy("//span[@aria-label='brand']");
}
