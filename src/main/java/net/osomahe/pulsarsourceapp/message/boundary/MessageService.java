package net.osomahe.pulsarsourceapp.message.boundary;

import net.osomahe.pulsarsourceapp.message.entity.CannotWritePulsarMessageException;
import org.apache.pulsar.client.api.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@RequestScoped
public class MessageService {
    private static final Logger log = Logger.getLogger(MessageService.class);

    @ConfigProperty(name = "pulsar.default.producer")
    String defaultProducer;

    @ConfigProperty(name = "pulsar.default.type")
    String defaultType;

    @ConfigProperty(name = "pulsar.default.tenant")
    String defaultTenant;

    @ConfigProperty(name = "pulsar.default.namespace")
    String defaultNamespace;

    @Inject
    PulsarClient pulsarClient;

    public void writeStringMessage(String topicName, String messageData, String producerName) {
        try (Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                .compressionType(CompressionType.LZ4)
                .sendTimeout(10, TimeUnit.SECONDS)
                .producerName(producerName != null && producerName.length() > 0 ? producerName : defaultProducer)
                .topic(getFullTopicName(topicName))
                .create()) {
            MessageId messageId = producer.send(messageData);
            log.debugf("Successfully written message: %s", messageId);
        } catch (PulsarClientException e) {
            throw new CannotWritePulsarMessageException(topicName, messageData, e);
        }
    }

    private String getFullTopicName(String topicName) {
        if (topicName.contains("/")) {
            return topicName;
        }
        return String.format("%s://%s/%s/%s", defaultType, defaultTenant, defaultNamespace, topicName);
    }
}
