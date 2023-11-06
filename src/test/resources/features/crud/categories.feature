Feature: CRUD operations on Categories via API

  Scenario Outline: Successfully add Categories
    Given the "<category>" category is not entered yet
    When I add category with name "<category>" and slug "<slug>"
    Then the "<category>" category is available

    Examples:
      | category   | slug       |
      | category 1 | category-1 |

