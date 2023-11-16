package com.example.csvreader.service.impl;

import com.example.csvreader.entity.Customer;
import com.example.csvreader.service.CSVReaderService;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVReaderServiceImplTest {

    @Test
    public void testReadCSV() throws IOException, CsvException {
        CSVReaderService csvReaderService = new CSVReaderServiceImpl();
        // Act
        List<Customer> customers = csvReaderService.readCSV("src/test/resources/1.csv");

        // Assert
        assertEquals(9, customers.size());

        Customer firstCustomer = customers.get(0);
        assertEquals("101", firstCustomer.getCustomerRef());
        assertEquals("John Smith", firstCustomer.getCustomerName());
        assertEquals("123 Main Street", firstCustomer.getAddressLine1());
        assertEquals("Apartment 4B", firstCustomer.getAddressLine2());
        assertEquals("Anytown", firstCustomer.getTown());
        assertEquals("Any County", firstCustomer.getCounty());
        assertEquals("United States", firstCustomer.getCountry());
        assertEquals("12345", firstCustomer.getPostcode());

        Customer ninthCustomer = customers.get(8);
        assertEquals("109", ninthCustomer.getCustomerRef());
        assertEquals("James Miller", ninthCustomer.getCustomerName());
        assertEquals("222 Redwood Avenue", ninthCustomer.getAddressLine1());
        assertEquals("", ninthCustomer.getAddressLine2());
        assertEquals("Townsville", ninthCustomer.getTown());
        assertEquals("County V", ninthCustomer.getCounty());
        assertEquals("Italy", ninthCustomer.getCountry());
        assertEquals("24680", ninthCustomer.getPostcode());
    }
}
