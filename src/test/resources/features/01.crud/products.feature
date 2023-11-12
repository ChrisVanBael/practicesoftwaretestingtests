Feature: CRUD operations on Products via API

  Scenario Outline: Successfully add Products
    Given the "<product>" product is not entered yet for "<brand>"
    When I add product with "<product>", "<description>", "<price>", "<category>", "<brand>" and image "<imageid>"
    Then the "<product>" product is available

    Examples:
      | product   | description             | price     | category   | brand        | imageid |
      | testprod  | dit is een test product | 3.95      | Hand Tools | Brand name 1 | 1       |

  Scenario: Successfully add Products, with actor memory
    Given following product is not entered yet
      | Product   | Description             | Price     | Category   | Brand        | Imageid |
      | testprod  | dit is een test product | 3.95      | Hand Tools | Brand name 1 | 1       |
    When I add that product
    Then that product is available