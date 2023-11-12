@crud @brands
Feature: CRUD operations on Brands via API

  @v1
  Scenario Outline: Successfully add Brands
    Given the "<brand>" is not entered yet
    When I add brand with name "<brand>" and slug "<slug>"
    Then the "<brand>" brand is available

    Examples:
      | brand   | slug    |
      | brand 1 | brand-1 |

