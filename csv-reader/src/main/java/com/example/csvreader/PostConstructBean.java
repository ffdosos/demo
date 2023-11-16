package com.example.csvreader;

import com.example.csvreader.entity.Customer;
import com.example.csvreader.service.CSVReaderService;
import com.example.csvreader.util.JsonUtil;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Log4j2
public class PostConstructBean {

    @Autowired
    CSVReaderService csvReaderService;

    @Value("${folder.path}")
    private String folderPath;

    @Value("${api.host.get}")
    private String getUrl;

    @Value("${api.host.save}")
    private String saveUrl;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("init success");

        var pathList = this.scanFile(folderPath);

        for (var path : pathList) {
            try {
                var customers = csvReaderService.readCSV(path);

                for (var customer : customers) {
                    save(customer);
                    String json = get(customer.getCustomerRef());
                    log.info("JSON: " + json);
                }

            } catch (CsvException | IOException e) {
                log.info("Read csv failed: " + e.getMessage());
            }
        }

        log.info("Finished");
        System.exit(0);

    }

    private void save(Customer customer) {
        log.info("Save customer: " + customer);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(saveUrl))
                .POST(HttpRequest.BodyPublishers.ofString(Objects.requireNonNull(JsonUtil.toJson(customer)), StandardCharsets.UTF_8))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Response Body: " + response.body());

        } catch (IOException | InterruptedException e) {
            log.error("Save customer failed: " + customer + e.getMessage());
        }
    }

    private String get(String customerRef) {
        log.info("Get customer: " + customerRef);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getUrl+customerRef))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Response Body: " + response.body());

            return response.body();
        } catch (IOException | InterruptedException e) {
            log.error("Get customer failed: " + customerRef + e.getMessage());
            return null;
        }
    }

    public List<String> scanFile(String folderPath) {
        var folder = new File(folderPath);
        var filePaths = new ArrayList<String>();

        log.info("Scanning: " + folder.getAbsolutePath());

        // Check if the folder exists
        if (folder.exists() && folder.isDirectory()) {
            // Get a list of all files in the folder
            File[] files = folder.listFiles();

            if (files != null) {

                // Iterate through the files and add their paths to the list
                for (File file : files) {
                    if (checkExtension(file.getAbsolutePath(), "csv")) {
                        log.info("found path:" + file.getAbsolutePath());
                        filePaths.add(file.getAbsolutePath());
                    }
                }
            } else {
                log.info("The folder is empty.");
            }
        } else {
            log.info("The folder does not exist or is not a directory.");
        }

        return filePaths;
    }

    public boolean checkExtension(String filename, String targetExtension) {
        var extension =  Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));

        return extension.isPresent() && extension.get().equalsIgnoreCase(targetExtension);
    }

}
