package net.osomahe.pulsarsourceapp.message.entity;

import org.apache.pulsar.client.api.PulsarClientException;

import java.util.StringJoiner;

public class CannotWritePulsarMessageException extends RuntimeException {
    public final String topicName;
    public final String messageData;

    public CannotWritePulsarMessageException(String topicName, String messageData, PulsarClientException e) {
        super(String.format("Cannot write to topic: %s messageData %s", topicName, messageData), e);
        this.topicName = topicName;
        this.messageData = messageData;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CannotWritePulsarMessageException.class.getSimpleName() + "[", "]")
                .add("topicName='" + topicName + "'")
                .add("messageData='" + messageData + "'")
                .toString();
    }
}
