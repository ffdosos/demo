package com.example.csvreader.service;

import com.example.csvreader.entity.Customer;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface CSVReaderService {

    List<Customer> readCSV(String filePath) throws IOException, CsvException;

}