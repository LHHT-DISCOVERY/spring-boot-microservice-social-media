package com.example.profile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreateRequest {
    String userId;
    String firstName;
    String lastName;
    String gender;
    LocalDate dob;
    String city;
    String email;
}
