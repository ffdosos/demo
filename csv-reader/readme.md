# 1. Console App to Read in a CSV File from a Directory

## Class Name: `PostConstructBean`

### Package
`com.example.csvreader`

### Description
This class is a Spring Component that is initialized after the Spring Application context is fully loaded. Its primary purpose is to handle CSV file processing and API interactions for customer data. It uses `CSVReaderService` to read customer data from CSV files, then saves and retrieves customer information via HTTP requests.

### Properties
- `csvReaderService`: An instance of `CSVReaderService` for processing CSV files.
- `folderPath`: A string representing the path to the folder containing CSV files.
- `getUrl`: The URL used for retrieving customer data via GET request.
- `saveUrl`: The URL used for saving customer data via POST request.

### Methods
- `init()`: Called when the application is fully ready. It scans for CSV files, reads customer data, and performs save and get HTTP operations.
    - **Returns**: Void
- `save(Customer customer)`: Sends a POST request to save customer data.
    - **Parameters**: `Customer customer` - The customer data to be saved.
    - **Returns**: Void
- `get(String customerRef)`: Sends a GET request to retrieve customer data.
    - **Parameters**: `String customerRef` - The reference of the customer to retrieve.
    - **Returns**: `String` - The response body of the GET request.
- `scanFile(String folderPath)`: Scans the specified folder path for CSV files.
    - **Parameters**: `String folderPath` - The path of the folder to scan.
    - **Returns**: `List<String>` - A list of file paths of the found CSV files.
- `checkExtension(String filename, String targetExtension)`: Checks if the given file has the specified extension.
    - **Parameters**:
        - `String filename` - The name of the file to check.
        - `String targetExtension` - The target file extension.
    - **Returns**: `boolean` - True if the file has the target extension, otherwise false.

### Annotations
- `@EventListener(ApplicationReadyEvent.class)`: Indicates that the `init()` method should be called when the application is ready.

### Notes
- This class is designed to be a part of a Spring Boot application.
- It uses `lombok` for logging and Spring's `@Value` annotation for external configuration.
- The class makes use of Java's HttpClient for API communication.
- It shuts down the application after processing the CSV files.

# 2. Parse the CSV file

## Class Name: `CSVReaderServiceImpl`

### Package
`com.example.csvreader.service.impl`

### Description
`CSVReaderServiceImpl` is a service class implementing the `CSVReaderService` interface. It's responsible for reading customer data from a CSV file and converting it into a list of `Customer` objects. This class uses the `opencsv` library for CSV file processing.

### Methods
- `readCSV(String filePath)`: Reads a CSV file from the given file path and converts it into a list of `Customer` objects.
  - **Parameters**: `String filePath` - The file path of the CSV file to be read.
  - **Returns**: `List<Customer>` - A list of `Customer` objects representing the data read from the CSV file.
  - **Exceptions**: `IOException`, `CsvException` - Thrown if there's an issue in file reading or CSV processing.
- `convert(String[] data)`: Converts an array of `String` values (representing a row from the CSV file) into a `Customer` object.
  - **Parameters**: `String[] data` - An array of string values representing a row from the CSV file.
  - **Returns**: `Customer` - The `Customer` object created from the provided data.

### Notes
- This class is part of the service layer in a Spring Boot application, specifically designed for CSV file processing related to customer data.
- The `opencsv` library is utilized for reading and parsing CSV files.
- Error handling is incorporated to manage `IOException` and `CsvException`.
- The class assumes a specific CSV format where the first line is presumably headers, which is skipped during processing.

# 3. REST API for saving and retrieving customer information

## CustomerController Class Description

### Package
`com.example.csvreader.controller`

### Description
The `CustomerController` class is a Spring MVC controller that handles HTTP requests related to `Customer` entities. It provides endpoints for creating and retrieving customer data, interfacing with the `CustomerService` for business logic.

### Methods
- `createCustomer(@RequestBody Customer customer)`: Handles POST requests to create a new customer.
  - **Parameters**: `@RequestBody Customer customer` - The `Customer` object to be created.
  - **Returns**: `ResponseEntity<Customer>` - A response entity containing the created `Customer` object.
- `getCustomer(@PathVariable String customerRef)`: Handles GET requests to retrieve a customer by their reference.
  - **Parameters**: `@PathVariable String customerRef` - The reference of the customer to be retrieved.
  - **Returns**: `ResponseEntity<Customer>` - A response entity containing the found `Customer` object, or a not-found status if the customer does not exist.

### Annotations
- `@Controller`: Marks this class as a Spring MVC controller.
- `@RequestMapping("/api/customers")`: Maps HTTP requests to the `/api/customers` URL to methods in this controller.
- `@Autowired`: Injects the `CustomerService` dependency into this controller.
- `@PostMapping` and `@GetMapping`: Annotations for mapping POST and GET HTTP methods, respectively.

## Notes
- This controller is part of the web layer in a Spring Boot application, handling web requests for customer-related operations.
- The `createCustomer` method expects a `Customer` object in the request body and returns the saved customer.
- The `getCustomer` method uses the `customerRef` path variable to look up and return a customer. It returns a not-found HTTP status if the customer doesn't exist.

## Examples 

### Create Customer Information

#### Request
```
curl --location 'http://localhost:8080/api/customers' \
--header 'Content-Type: application/json' \
--data '{
"customerRef": "2",
"customerName": "XXX",
"addressLine1": "3432423",
"addressLine2": "234234",
"town": "twon",
"county": "county",
"country": "country",
"postcode": "342424"
}'
```
#### Response
```
{
    "customerRef": "2",
    "customerName": "XXX",
    "addressLine1": "3432423",
    "addressLine2": "234234",
    "town": "twon",
    "county": "county",
    "country": "country",
    "postcode": "342424"
}
```
### Retrieve Customer Information

#### Request
```
curl --location 'http://localhost:8080/api/customers/1'
```
#### Response

```
{
    "customerRef": "1",
    "customerName": "Gordon",
    "addressLine1": "3432423",
    "addressLine2": "234234",
    "town": "twon",
    "county": "county",
    "country": "country",
    "postcode": "342424"
}
```



