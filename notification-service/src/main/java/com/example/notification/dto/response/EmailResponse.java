package com.example.notification.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailResponse {
    String messageId;
}
