package com.tesuqa.practicesoftwaretestingtests.screenplay.abilities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class PaginatedResponseDeserializer<T> extends JsonDeserializer<T> {
    private final Class<T> targetClass;

    public PaginatedResponseDeserializer(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jp.getCodec();
        JsonNode node = codec.readTree(jp);
        ObjectMapper mapper = (ObjectMapper) codec;

        try {
            // Create instance of the target class
            T instance = targetClass.getDeclaredConstructor().newInstance();

            // Set pagination metadata fields
            if (node.has("current_page")) {
                Method setCurrentPage = targetClass.getMethod("setCurrentPage", Integer.class);
                setCurrentPage.invoke(instance, node.get("current_page").asInt());
            }

            if (node.has("last_page")) {
                Method setLastPage = targetClass.getMethod("setLastPage", Integer.class);
                setLastPage.invoke(instance, node.get("last_page").asInt());
            }

            if (node.has("per_page")) {
                Method setPerPage = targetClass.getMethod("setPerPage", Integer.class);
                setPerPage.invoke(instance, node.get("per_page").asInt());
            }

            if (node.has("from")) {
                Method setFrom = targetClass.getMethod("setFrom", Integer.class);
                setFrom.invoke(instance, node.get("from").asInt());
            }

            if (node.has("to")) {
                Method setTo = targetClass.getMethod("setTo", Integer.class);
                setTo.invoke(instance, node.get("to").asInt());
            }

            if (node.has("total")) {
                Method setTotal = targetClass.getMethod("setTotal", Integer.class);
                setTotal.invoke(instance, node.get("total").asInt());
            }

            // Handle the data array - this requires special handling based on the type
            if (node.has("data") && node.get("data").isArray()) {
                JsonNode dataNode = node.get("data");

                // Get the generic type of the data list
                Method getDataMethod = targetClass.getMethod("getData");
                Type returnType = getDataMethod.getGenericReturnType();

                // Get the element type from the generic return type
                Class<?> elementType = (Class<?>) ((ParameterizedType) returnType).getActualTypeArguments()[0];

                // Create a collection type for the list of elements
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, elementType);

                // Convert the JSON array to a List of the appropriate type
                List<?> dataList = mapper.convertValue(dataNode, listType);

                // Set the data list
                Method setData = targetClass.getMethod("setData", List.class);
                setData.invoke(instance, dataList);
            }

            return instance;
        } catch (Exception e) {
            throw new IOException("Failed to deserialize paginated response: " + e.getMessage(), e);
        }
    }
}
