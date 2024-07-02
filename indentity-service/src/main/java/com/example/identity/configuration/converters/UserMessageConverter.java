package com.example.identity.configuration.converters;

import com.example.identity.entity.User;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserMessageConverter extends JsonMessageConverter {
    // mapping json to object in java when sent object to kafka
    // target is covert message that producer push into kafka mapping to object of java for consumer
    //  (simple understand with data type is json at producer change object of java at consumer)
    public UserMessageConverter() {
        super();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("com.example.identity.entity");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("user", User.class); // can put many key-value to map to processing 1 topic and have many different messages (examples : 1 topic and 1 message)
        typeMapper.setIdClassMapping(mappings); // mapping "user" to "UserResponse.class" , note that ( "user" same "user:" config kafka in yml file)
        this.setTypeMapper(typeMapper);
    }
}
