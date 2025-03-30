Feature: Create Brand, Category and Product

  Scenario: Verify a created product is displayed
    Given I add the new brand "myBrand"
      And I add the new category "myCategory"
     When I add following new product for this brand and category
      | Product  | Description             | Price | Location | Rental |
      | testprod2 | dit is een test product | 3.95  | false    | false  |
     Then that product can be found on the homepage