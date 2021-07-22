package net.osomahe.pulsarsourceapp.health.control;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Readiness
@ApplicationScoped
public class PulsarReadinessCheck implements HealthCheck {

    private static final Logger log = Logger.getLogger(PulsarReadinessCheck.class);

    @Inject
    @ConfigProperty(name = "pulsar.service-url")
    String serviceUrl;

    @Inject
    PulsarClient pulsarClient;

    private Producer<byte[]> producer;


    @Override
    @Timeout(500)
    public HealthCheckResponse call() {
        if (producer == null) {
            try {
                producer = pulsarClient.newProducer().topic("health-check").create();
            } catch (PulsarClientException e) {
                log.warnf("Cannot connect to Apache Pulsar on url: %s", serviceUrl);
            }
        }
        boolean connected = producer != null && producer.isConnected();
        if (!connected) {
            producer = null;
        }
        return HealthCheckResponse.builder()
                .name("Apache Pulsar connection health check")
                .withData("pulsarUrl", serviceUrl)
                .withData("connected", connected)
                .status(connected)
                .build();
    }
}
