package com.example.identity.apache_kafka.kafka_configurations;

import com.example.identity.apache_kafka.kafka_topics.KafkaTopicContain;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfiguration {
    // kafka cluster includes : topic (include many partition) , partition (include many offset), and replicate (phân thân backup)
    // create a new topic in kafka cluster with name "topic" and number of "partition" and "replication"
    @Bean
    public NewTopic userRegister(){
        return new NewTopic(KafkaTopicContain.MESSAGE_USER_REGISTER_SUCCESS, 1, (short) 1); // topic , partitions , replicate
    }

    @Bean
    public NewTopic dataUserRegister() {
        return new NewTopic(KafkaTopicContain.DATA_USER_REGISTER_SUCCESS, 1, (short) 1); // topic , partitions , replicate
    }
}
