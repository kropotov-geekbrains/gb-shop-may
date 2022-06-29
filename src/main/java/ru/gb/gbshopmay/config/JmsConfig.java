package ru.gb.gbshopmay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import ru.gb.gbshopmay.modelMessage.ChangePricedMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Kropotov
 * created at 08.06.2022
 **/
@Configuration
public class JmsConfig {

    public static final String CHANGE_PRICE_PRODUCT_QUEUE = "change-price-product";
    public static final String GB_QUEUE_RECEIVE = "gb-queue-receive";

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
        typeIdMappings.put("_type", ChangePricedMessage.class);

        converter.setTypeIdMappings(typeIdMappings);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
