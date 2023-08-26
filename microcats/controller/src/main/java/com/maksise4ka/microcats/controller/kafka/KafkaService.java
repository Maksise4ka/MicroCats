package com.maksise4ka.microcats.controller.kafka;

import com.maksise4ka.microcats.controller.exceptions.SynchronouslyRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafka;
    private final KafkaTemplate<String, Object> kafka;

    public <T> T sendSynchronously(Object request, String topic, Class<T> awaitedType) {

        final ConsumerRecord<String, Object> consumerRecord;
        try {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, request);
            RequestReplyFuture<String, Object, Object> replyFuture = replyingKafka.sendAndReceive(record);
            SendResult<String, Object> sendResult = replyFuture.getSendFuture().get();
            consumerRecord = replyFuture.get();
        }
        catch (ExecutionException | InterruptedException e) {
            throw SynchronouslyRequestException.kafkaThrowsException();
        }

        Object reply = consumerRecord.value();

        try {
            return awaitedType.cast(reply);
        }
        catch (ClassCastException e) {
            throw SynchronouslyRequestException.unexpectedTypeReceived(awaitedType, reply.getClass());
        }
    }

    public void sendAsynchronously(Object request, String topic) {
        kafka.send(topic, request);
    }
}
