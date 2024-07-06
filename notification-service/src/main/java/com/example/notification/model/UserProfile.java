package com.example.notification.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfile {
    String id;
    String firstName;
    String lastName;
    String gender;
    LocalDate dob;
    String city;
    String email;
}
