Feature: Retrieve invoices using a protected API

  Scenario: User logs in and retrieves invoices
    Given the user is logged in with email "customer@practicesoftwaretesting.com" and password "welcome01"
    When the user retrieves the invoices
    Then 7 invoices should be successfully retrieved