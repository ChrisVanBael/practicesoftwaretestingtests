package com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api;

import com.practicesoftwaretesting.client.v1.model.ProductResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseProductsApi;
import net.serenitybdd.screenplay.Question;

import java.util.List;

public class TheProducts {

    /**
     * Returns a list of ProductResponses known by the system
     * @return list of ProductResponses
     */
    public static Question<List<ProductResponse>> knownByTheSystem() {
        return Question.about("the products known by the system")
                .answeredBy(
                        actor -> UseProductsApi.as(actor).getAllProducts(null, null, null, null, null)
                );
    }

    /**
     * Returns a list of Products where the name partially or fully matches the query
     * @param query part of the name of the Product
     * @return List of ProductResponses
     */
    public static Question<List<ProductResponse>> foundWithQuery(String query) {
        return Question.about("the products found with query: " + query)
            .answeredBy(
                actor -> UseProductsApi.as(actor).search(query)
            );
    }

    /**
     * Returns a list of ProductResponses for a specific brand
     * @return list of ProductResponses
     */
    public static Question<List<ProductResponse>> ofBrand(String brandId) {
        return Question.about("the products of brand")
                .answeredBy(
                        actor -> UseProductsApi.as(actor).getAllProducts(brandId, null, null, null, null)
                );
    }

    /**
     * Returns a list of ProductResponses for a specific category
     * @return list of ProductResponses
     */
    public static Question<List<ProductResponse>> inCategory(String categoryId) {
        return Question.about("the products in category")
                .answeredBy(
                        actor -> UseProductsApi.as(actor).getAllProducts(null, categoryId, null, null, null)
                );
    }

    /**
     * Returns a list of ProductResponses that are rental
     * @return list of ProductResponses
     */
    public static Question<List<ProductResponse>> thatAreRental(String isRental) {
        return Question.about("the products that are rental")
                .answeredBy(
                        actor -> UseProductsApi.as(actor).getAllProducts(null, null, isRental, null, null)
                );
    }

    /**
     * Returns a list of ProductResponses that are filtered according to brandId, categoryId and/or rental
     * @return list of ProductResponses
     */
    public static Question<List<ProductResponse>> filteredAccording(String brandId, String categoryId, String isRental) {
        return Question.about("the products that are filtered")
                .answeredBy(
                        actor -> UseProductsApi.as(actor).getAllProducts(brandId, categoryId, isRental, null, null)
                );
    }
}
