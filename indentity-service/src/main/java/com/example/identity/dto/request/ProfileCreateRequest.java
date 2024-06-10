package com.example.identity.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreateRequest {
    String userId; // same property in Profile Entity in Profile service
    String firstName;
    String lastName;
    LocalDate dob;
    String gender;
    String city;
}
