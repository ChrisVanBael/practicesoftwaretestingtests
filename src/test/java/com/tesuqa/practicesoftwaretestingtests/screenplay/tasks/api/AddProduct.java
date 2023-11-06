package com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api;

import com.practicesoftwaretesting.client.model.ProductRequest;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseProductsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheId;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import java.math.BigDecimal;


public class AddProduct {

    /**
     * Adds the Product with name and values
     * @param name name of the product
     * @param description description of the product
     * @param price price of the product
     * @param categoryName name of the category of the product
     * @param brandName name of the brand of the product
     * @param imageId id of the image to use
     * @return the task
     */
    public static Performable withValues(String name, String description, String price, String categoryName, String brandName, String imageId) {
        return Task.where(
                actor -> {
                    ProductRequest newProduct = new ProductRequest();
                    newProduct.setName(name);
                    newProduct.setDescription(description);
                    newProduct.setPrice(new BigDecimal(price));
                    newProduct.setCategoryId(actor.asksFor(TheId.ofCategory(categoryName)));
                    newProduct.setBrandId(actor.asksFor(TheId.ofBrand(brandName)));
                    newProduct.setProductImageId(Integer.parseInt(imageId));
                    UseProductsApi.as(actor).createProduct(newProduct);
                }
        );
    }

    /**
     * Adds the product with a ProductRequest
     * @param newProduct ProductRequest object to create
     * @return the task
     */
    public static Performable withProduct(ProductRequest newProduct){
        return Task.where(
                actor -> UseProductsApi.as(actor).createProduct(newProduct)
        );
    }
}
