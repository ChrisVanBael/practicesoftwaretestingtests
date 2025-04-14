package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.exceptions;

/**
 * Specific exception for resource not found errors (404 status code)
 */
public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String message, int statusCode, String responseBody) {
        super(message, statusCode, responseBody);
    }
}