Feature: CRUD operations on Brands via API

  Scenario Outline: Successfully add Brands
    Given the "<brand>" brand is not entered yet
    When I add brand with name "<brand>" and slug "<slug>"
    Then the "<brand>" brand is available

    Examples:
      | brand   | slug    |
      | brand 1 | brand-1 |

