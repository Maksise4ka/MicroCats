package com.maksise4ka.microcats.owners;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaListenerErrorHandler errorHandler() {
        return new CustomErrorHandler();
    }
}
