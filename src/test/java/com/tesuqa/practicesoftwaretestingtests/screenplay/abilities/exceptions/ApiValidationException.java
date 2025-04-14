package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.exceptions;

/**
 * Specific exception for validation errors (422 status code)
 */
public class ApiValidationException extends ApiException {
    public ApiValidationException(String message, int statusCode, String responseBody) {
        super(message, statusCode, responseBody);
    }
}