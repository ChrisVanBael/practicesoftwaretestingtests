Feature: CRUD operations on Brands via API

  Scenario Outline: Successfully add Brands
    Given the "<brand>" is not entered yet
    When I add brand with name "<brand>" and slug "<slug>"
    Then the "<brand>" is available for use with "<slug>"

    Examples:
      | brand   | slug    |
      | brand 1 | brand-1 |

