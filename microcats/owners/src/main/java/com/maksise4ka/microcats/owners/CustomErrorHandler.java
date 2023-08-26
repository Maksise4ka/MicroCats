package com.maksise4ka.microcats.owners;

import com.maksise4ka.microcats.contracts.ErrorMessage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;

public class CustomErrorHandler implements KafkaListenerErrorHandler {
    @Override
    @Nonnull
    public Object handleError(Message<?> message, @Nullable ListenerExecutionFailedException exception) {
        return new ErrorMessage("hello?");
    }

    @Override
    @Nonnull
    public Object handleError(Message<?> message, @Nullable ListenerExecutionFailedException exception, @Nullable Consumer<?, ?> consumer) {
        return new ErrorMessage("hello?");
    }

    @Override
    @Nonnull
    public Object handleError(Message<?> message, @Nullable ListenerExecutionFailedException exception, @Nullable Consumer<?, ?> consumer, @Nullable Acknowledgment ack) {
        return new ErrorMessage("hello?");
    }
}
