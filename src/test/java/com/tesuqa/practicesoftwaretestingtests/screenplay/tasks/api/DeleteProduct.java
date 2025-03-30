package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api;

import com.practicesoftwaretesting.client.v5.model.ProductResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseProductsApi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;


public class DeleteProduct {

    /**
     * Deletes the Product with name
     * TODO: User should be first logged in as admin to be able to delete products
     * @param name name of the product
     * @return the task
     */
    public static Performable withName(String name) {
        return Task.where(
                actor -> {
                    String id = UseProductsApi.as(actor).getAllProducts(null, null, null)
                            .stream()
                            .filter(productResponse -> productResponse.getName().equals(name))
                            .map(ProductResponse::getId)
                            .findFirst().get();
                    UseProductsApi.as(actor).deleteProduct(id);
                }
        );
    }
}
