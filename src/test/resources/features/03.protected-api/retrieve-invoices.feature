Feature: Retrieve invoices using a protected API

  @to_run
  Scenario: User logs in and retrieves invoices
    Given the user is logged in with email "customer@practicesoftwaretesting.com" and password "welcome01"
    When the user retrieves the invoices
    Then the number of invoices should be greater than 0