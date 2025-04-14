Feature: CRUD operations on Products via API
  @to_run
  Scenario Outline: Successfully add Products
    Given the "<product>" product is not entered yet for "<brand>"
    When I add product with "<product>", "<description>", "<price>", "<category>", "<brand>", "<location>" and "<rental>"
    Then the "<product>" product is available

    Examples:
      | product   | description             | price     | category   | brand  | location | rental |
      | testprod1 | dit is een test product | 3.95      | Hand Tools | Alan   | false    | false  |

  @to_run
  Scenario: Successfully add Products, with actor memory
    Given following product is not entered yet
      | Product    | Description             | Price     | Category   | Brand | Location | Rental |
      | testprod1  | dit is een test product | 3.95      | Hand Tools | Alan  | false    | false  |
    When I add that product
    Then that product is available