package com.tesuqa.practicesoftwaretestingtests.stepdefinitions;

import com.practicesoftwaretesting.client.model.ProductRequest;
import com.practicesoftwaretesting.client.model.ProductResponse;
import com.tesuqa.practicesoftwaretestingtests.pages.HomePage;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseBrandsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseCategoriesApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.UseProductsApi;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.web.TheProductNames;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.web.NavigateTo;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheId;
import com.tesuqa.practicesoftwaretestingtests.screenplay.questions.api.TheProducts;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.AddProduct;
import com.tesuqa.practicesoftwaretestingtests.screenplay.tasks.api.DeleteProduct;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class ProductStepDefinitions {

    private EnvironmentVariables environmentVariables;
    private Actor myActor;
    private WebDriver browser;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        String theRestApiBaseUrl = EnvironmentSpecificConfiguration
                .from(environmentVariables).getProperty("api.base.url");
        myActor = Actor.named("MyActor");
        myActor.whoCan(UseProductsApi.at(theRestApiBaseUrl));
        myActor.whoCan(UseBrandsApi.at(theRestApiBaseUrl));
        myActor.whoCan(UseCategoriesApi.at(theRestApiBaseUrl));
        myActor.whoCan(BrowseTheWeb.with(browser));
    }

    /**
     * Verifies the product name doesn't exist yet. If it exists, deletes it
     * @param productName name of the product
     * @param brandName name of the brand
     */
    @Given("the {string} product is not entered yet for {string}")
    public void assureProductNotEntered(String productName, String brandName) {
        Integer brandId = myActor.asksFor(TheId.ofBrand(brandName));
        List<String> allProductNames = myActor.asksFor(TheProducts.ofBrand(brandId))
                .stream()
                .map(ProductResponse::getName)
                .collect(Collectors.toList());
        if (allProductNames.contains(productName)) {
            myActor.attemptsTo(DeleteProduct.withName(productName));
        }
    }


    /**
     * Adds a product with its values
     * @param productName name of the product
     * @param description description of the product
     * @param price price of the product
     * @param category name of the category
     * @param brandName name of the brand
     * @param imageId ID of the image
     */
    @When("I add product with {string}, {string}, {string}, {string}, {string} and image {string}")
    public void addProductWithValues(String productName, String description, String price, String category, String brandName, String imageId) {
        myActor.attemptsTo(AddProduct.withValues(productName, description, price, category, brandName, imageId));
    }

    /**
     * Verifies that the product is in the system
     * @param productName the name of the product
     */
    @Then("the {string} product is available")
    public void verifyProductAvailable(String productName) {
        List<String> allProductNames = myActor.asksFor(TheProducts.knownByTheSystem())
                .stream()
                .map(ProductResponse::getName)
                .collect(Collectors.toList());
        myActor.attemptsTo(Ensure.that(productName).isIn(allProductNames));
    }

    /**
     * Verifies the product name doesn't exist yet. If it exists, deletes it
     * @param dt DataTable with the fields for a ProductRequest <br>
     * <b>Memory Read</b>: nothing <br>
     * <b>Memory Write</b>: "New Product" <br>
     */
    @Given("following product is not entered yet")
    public void followingProductIsNotEnteredYet(DataTable dt) {
        // create a ProductRequest and remember it in the memory of the actor
        List<String> productList = dt.cells().stream().skip(1).findFirst().get();
        ProductRequest newProduct = new ProductRequest();
        newProduct.setName(productList.get(0));
        newProduct.setDescription(productList.get(1));
        newProduct.setPrice(new BigDecimal(productList.get(2)));
        newProduct.setCategoryId(myActor.asksFor(TheId.ofCategory(productList.get(3))));
        newProduct.setBrandId(myActor.asksFor(TheId.ofBrand(productList.get(4))));
        newProduct.setProductImageId(Integer.parseInt(productList.get(5)));
        myActor.remember("New Product", newProduct);

        // verify the product does not exist yet
        List<String> allProductNames = myActor.asksFor(TheProducts.ofBrand(newProduct.getBrandId()))
                .stream()
                .map(ProductResponse::getName)
                .collect(Collectors.toList());
        if (allProductNames.contains(newProduct.getName())) {
            myActor.attemptsTo(DeleteProduct.withName(newProduct.getName()));
        }
    }

    /**
     * Adds the previously remembered product <br>
     * <b>Memory Read</b>: "New Product" <br>
     * <b>Memory Write</b>: nothing <br>
     */
    @When("I add that product")
    public void addProductWithRequest() {
        ProductRequest newProduct = myActor.recall("New Product");
        myActor.attemptsTo(AddProduct.withProduct(newProduct));
    }

    /**
     * Verifies the product is available in the API <br>
     * <b>Memory Read</b>: "New Product" <br>
     * <b>Memory Write</b>: nothing <br>
     */
    @Then("that product is available")
    public void verifyProductIsAvailableInAPI() {
        ProductRequest newProduct = myActor.recall("New Product");

        List<String> allProductNames = myActor.asksFor(TheProducts.knownByTheSystem())
                .stream()
                .map(ProductResponse::getName)
                .collect(Collectors.toList());
        myActor.attemptsTo(Ensure.that(newProduct.getName()).isIn(allProductNames));
    }

    /**
     * Verifies the product is available on the homepage <br>
     * <b>Memory Read</b>: "New Product" <br>
     * <b>Memory Write</b>: nothing <br>
     */
    @Then("that product can be found on the homepage")
    public void verifyProductIsAvailableWeb() {
        ProductRequest newProduct = myActor.recall("New Product");

        myActor.attemptsTo(Open.browserOn().the(HomePage.class));
        List<String> allProductNames = myActor.asksFor(TheProductNames.onTheHomePage());
        myActor.attemptsTo(Ensure.that(newProduct.getName()).isIn(allProductNames));

        // Could also be written as:
        //myActor.attemptsTo(Ensure.that(newProduct.getName()).isIn(TheProductNames.onTheHomePage().answeredBy(myActor)));
    }
}
