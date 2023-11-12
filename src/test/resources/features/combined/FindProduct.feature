Feature: Add and find a product

  Scenario:
    Given following product is not entered yet
      | Product   | Description             | Price     | Category   | Brand        | Imageid |
      | testprod  | dit is een test product | 3.95      | Hand Tools | Brand name 1 | 1       |
    When I add that product
    Then that product can be found on the homepage
