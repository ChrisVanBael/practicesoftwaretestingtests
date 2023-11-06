package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api;

import com.practicesoftwaretesting.client.model.BrandRequest;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;


public class AddBrand {

    /**
     * Adds the Brand with name
     * @param name name of the brand
     * @return the task
     */
    public static Performable withNameAndSlug(String name, String slug) {
        return Task.where(
                actor -> {
                    BrandRequest newBrand = new BrandRequest();
                    newBrand.setName(name);
                    newBrand.setSlug(slug);
                    UseBrandsApi.as(actor).createBrand(newBrand);
                }
        );
    }
}
