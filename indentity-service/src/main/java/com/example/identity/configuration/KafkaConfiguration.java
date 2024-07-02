package com.example.identity.configuration;

import com.example.identity.common.util.KafkaTopicContain;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfiguration {
    // kafka cluster includes : topic (include many partition) , partition (include many offset), and replicate (phân thân backup)
    @Bean
    public NewTopic userRegister(){
        return new NewTopic(KafkaTopicContain.USER_REGISTER_SUCCESS, 1, (short) 1); // topic , partitions , replicate
    }
}
