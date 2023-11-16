package com.example.csvreader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    String customerRef;
    String customerName;
    String addressLine1;
    String addressLine2;
    String town;
    String county;
    String country;
    String postcode;

}
