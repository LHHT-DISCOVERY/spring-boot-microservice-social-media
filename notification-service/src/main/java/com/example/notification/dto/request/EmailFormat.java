package com.example.notification.dto.request;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailFormat {
    String name;
    String email;
}
