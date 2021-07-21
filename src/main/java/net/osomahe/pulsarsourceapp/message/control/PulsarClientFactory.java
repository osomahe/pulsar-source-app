package net.osomahe.pulsarsourceapp.message.control;

import io.quarkus.runtime.ShutdownEvent;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class PulsarClientFactory {

    @Inject
    @ConfigProperty(name = "pulsar.service-url")
    String serviceUrl;
    @Inject
    @ConfigProperty(name = "pulsar.tls-trust-certs-file")
    String tlsTrustCertsFile;
    @Inject
    @ConfigProperty(name = "pulsar.tls-cert-file")
    String tlsCertFile;
    @Inject
    @ConfigProperty(name = "pulsar.tls-key-file")
    String tlsKeyFile;

    PulsarClient pulsarClient;

    private void init() {
        try {
            this.pulsarClient = PulsarClient.builder()
                    .serviceUrl(serviceUrl)
                    /*.tlsTrustCertsFilePath(tlsTrustCertsFile)
                    .authentication(
                            "org.apache.pulsar.client.impl.auth.AuthenticationTls",
                            "tlsCertFile:" + tlsCertFile + ",tlsKeyFile:" + tlsKeyFile
                    )*/
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    @Produces
    public PulsarClient getPulsarClient() {
        if (this.pulsarClient == null) {
            init();
        }
        return this.pulsarClient;
    }

    void shutdown(@Observes ShutdownEvent event) {
        try {
            this.pulsarClient.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

}
