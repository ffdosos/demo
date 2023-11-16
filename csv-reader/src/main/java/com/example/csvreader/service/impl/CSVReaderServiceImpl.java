package com.example.csvreader.service.impl;

import com.example.csvreader.entity.Customer;
import com.example.csvreader.service.CSVReaderService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVReaderServiceImpl implements CSVReaderService {

    public List<Customer> readCSV(String filePath) throws IOException, CsvException {
        List<Customer> customers = null;

        try (var csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> csvData = csvReader.readAll();

            if (!csvData.isEmpty()) {
                csvData.remove(0);

                customers = csvData.stream()
                        .map(this::convert)
                        .collect(Collectors.toList());
            }
        }

        return customers;
    }

    private Customer convert(String[] data) {
        var customer = new Customer();
        customer.setCustomerRef(data[0]);
        customer.setCustomerName(data[1]);
        customer.setAddressLine1(data[2]);
        customer.setAddressLine2(data[3]);
        customer.setTown(data[4]);
        customer.setCounty(data[5]);
        customer.setCountry(data[6]);
        customer.setPostcode(data[7]);
        return customer;
    }
}