Feature: Create Brand, Category and Product

  Scenario: Verify a created product is displayed
    Given I add the new brand "myBrand"
      And I add the new category "myCategory"
     When I add following new product for this brand and category
      | Product  | Description             | Price | Imageid |
      | testprod | dit is een test product | 3.95  | 1       |
     Then that product can be found on the homepage