package com.example.csvreader.util;

import com.example.csvreader.entity.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonUtilTest {

    @Test
    public void testToJson() throws JsonProcessingException {
        // Create a Customer object to be converted to JSON
        Customer customer = Customer.builder()
                .customerRef("12345")
                .customerName("John Doe")
                .addressLine1("123 Main St")
                .town("Anytown")
                .county("Some County")
                .country("US")
                .postcode("12345")
                .build();

        // Run
        String json = JsonUtil.toJson(customer);

        // Assert
        assertEquals("{\"customerRef\":\"12345\",\"customerName\":\"John Doe\",\"addressLine1\":\"123 Main St\",\"addressLine2\":null,\"town\":\"Anytown\",\"county\":\"Some County\",\"country\":\"US\",\"postcode\":\"12345\"}", json);
    }
}