package com.example.identity.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    String username;
    String password;
}
