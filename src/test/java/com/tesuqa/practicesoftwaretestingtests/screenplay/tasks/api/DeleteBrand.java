package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api;

import com.practicesoftwaretesting.client.model.BrandResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;


public class DeleteBrand {

    /**
     * Deletes the Brand with name
     * @param name name of the brand
     * @return the task
     */
    public static Performable withName(String name) {
        return Task.where(
                actor -> {
                    Integer id = UseBrandsApi.as(actor).getAllBrands()
                            .stream()
                            .filter(brandResponse -> brandResponse.getName().equals(name))
                            .map(BrandResponse::getId)
                            .findFirst().get();
                    UseBrandsApi.as(actor).deleteBrand(id);
                }
        );
    }
}
