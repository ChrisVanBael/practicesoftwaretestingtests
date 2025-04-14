package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.practicesoftwaretesting.client.v5.model.PaginatedInvoiceResponse;
import com.practicesoftwaretesting.client.v5.model.PaginatedProductResponse;
import com.practicesoftwaretesting.client.v5.model.PaginatedUserResponse;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.exceptions.ApiException;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.exceptions.ApiValidationException;
import com.tesuqa.practicesoftwaretestingtests.screenplay.abilities.exceptions.ResourceNotFoundException;
import com.tesuqa.practicesoftwaretestingtests.utilities.TestLogger;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Ability;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

/**
 * Base class for all API-related abilities
 */
public abstract class BaseApiAbility implements Ability, RefreshableApi {

    protected final String baseUrl;
    protected static final TestLogger logger = TestLogger.auto();
    protected final ObjectMapper objectMapper;

    protected BaseApiAbility(String baseUrl) {
        this.baseUrl = baseUrl;
        ApiClientManager.getInstance(baseUrl).registerDependentApi(this);

        // Initialize and configure ObjectMapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Register custom deserializers for paginated responses
        SimpleModule module = new SimpleModule();
        module.addDeserializer(PaginatedProductResponse.class,
            new PaginatedResponseDeserializer<>(PaginatedProductResponse.class));
        module.addDeserializer(PaginatedInvoiceResponse.class,
            new PaginatedResponseDeserializer<>(PaginatedInvoiceResponse.class));
        // Add more deserializers for other paginated response types as needed

        this.objectMapper.registerModule(module);

        // Configure RestAssured to use our custom ObjectMapper
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
            new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (type, charset) -> objectMapper
            )
        );
    }


    @Override
    public String toString() {
        return "call the API at " + baseUrl;
    }

    /**
     * Generic method to execute API calls with proper error handling and logging
     * for single object responses
     */
    protected <T> T executeApiCall(String operationName, Supplier<Response> apiCall, Class<T> responseType) {
        logger.info(this.getClass().getSimpleName(), "Executing %s", operationName);

        try {
            // Clear the request bodies between requests
            RestAssured.reset();

            Response response = apiCall.get();
            int statusCode = response.getStatusCode();
            String responseBody = response.getBody().asString();

            logger.debug(this.getClass().getSimpleName(), "Response for %s: Status=%d, Body=%s",
                operationName, statusCode, responseBody);

            if (statusCode >= 200 && statusCode < 300) {
                // Success case - manually deserialize using our ObjectMapper
                T result = objectMapper.readValue(responseBody, responseType);
                logger.info(this.getClass().getSimpleName(), "%s completed successfully with status %d",
                    operationName, statusCode);
                return result;
            } else if (statusCode == 422) {
                // Validation error
                logger.error(this.getClass().getSimpleName(), "Validation error in %s: %s",
                    operationName, responseBody);
                throw new ApiValidationException("Validation failed for " + operationName + ": " + responseBody,
                    statusCode, responseBody);
            } else if (statusCode == 404) {
                // Not found error
                logger.error(this.getClass().getSimpleName(), "Resource not found in %s: %s",
                    operationName, responseBody);
                throw new ResourceNotFoundException("Resource not found for " + operationName + ": " + responseBody,
                    statusCode, responseBody);
            } else {
                // Other error
                logger.error(this.getClass().getSimpleName(), "Error in %s: Status %d, Response: %s",
                    operationName, statusCode, responseBody);
                throw new ApiException("Failed to " + operationName + ": " + responseBody,
                    statusCode, responseBody);
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw (ApiException) e;
            }
            logger.error(this.getClass().getSimpleName(), "Exception in %s: %s",
                operationName, e.getMessage());
            throw new ApiException("Failed to " + operationName + " due to: " + e.getMessage(), e);
        }
    }

    /**
     * Generic method to execute API calls with proper error handling and logging
     * for list responses
     */
    protected <T> List<T> executeApiCallForList(String operationName, Supplier<Response> apiCall, Class<T> elementType) {
        logger.info(this.getClass().getSimpleName(), "Executing %s for list of %s",
            operationName, elementType.getSimpleName());

        try {
            Response response = apiCall.get();
            int statusCode = response.getStatusCode();
            String responseBody = response.getBody().asString();

            logger.debug(this.getClass().getSimpleName(), "Response for %s: Status=%d, Body=%s",
                operationName, statusCode, responseBody);

            if (statusCode >= 200 && statusCode < 300) {
                // Success case - manually deserialize using our ObjectMapper
                CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, elementType);
                List<T> result = objectMapper.readValue(responseBody, listType);

                logger.info(this.getClass().getSimpleName(), "%s completed successfully with status %d, found %d items",
                    operationName, statusCode, result.size());
                return result;
            } else if (statusCode == 422) {
                // Validation error
                logger.error(this.getClass().getSimpleName(), "Validation error in %s: %s",
                    operationName, responseBody);
                throw new ApiValidationException("Validation failed for " + operationName + ": " + responseBody,
                    statusCode, responseBody);
            } else if (statusCode == 404) {
                // Not found error
                logger.error(this.getClass().getSimpleName(), "Resource not found in %s: %s",
                    operationName, responseBody);
                throw new ResourceNotFoundException("Resource not found for " + operationName + ": " + responseBody,
                    statusCode, responseBody);
            } else {
                // Other error
                logger.error(this.getClass().getSimpleName(), "Error in %s: Status %d, Response: %s",
                    operationName, statusCode, responseBody);
                throw new ApiException("Failed to " + operationName + ": " + responseBody,
                    statusCode, responseBody);
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw (ApiException) e;
            }
            logger.error(this.getClass().getSimpleName(), "Exception in %s: %s",
                operationName, e.getMessage());
            throw new ApiException("Failed to " + operationName + " due to: " + e.getMessage(), e);
        }
    }

    /**
     * Helper method to log detailed information about a response for debugging
     */
    protected void logDetailedResponse(String operationName, Response response) {
        logger.debug(this.getClass().getSimpleName(),
            "Detailed response for %s:\nStatus Code: %d\nHeaders: %s\nBody: %s",
            operationName,
            response.getStatusCode(),
            response.getHeaders(),
            response.getBody().prettyPrint());
    }
}
