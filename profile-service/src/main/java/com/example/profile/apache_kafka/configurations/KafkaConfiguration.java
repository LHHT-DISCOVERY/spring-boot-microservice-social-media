package com.example.profile.apache_kafka.configurations;

import com.example.profile.apache_kafka.kafka_topics.KafkaTopicContain;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfiguration {

    @Bean
    public NewTopic userProfileUpdated(){
        return new NewTopic(KafkaTopicContain.PROFILE_CREATE_USER_SUCCESS, 1, (short) 1); // topic, partitions, replicate
    }

}
