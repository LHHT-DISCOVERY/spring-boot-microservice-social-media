package com.example.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.StringBuilders;

import java.util.Map;

@Data
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {
    String channelName;
    String recipient;
    String templateCode;
    Map<String, Object> param;
    String subject;
    String body;
}
