package net.osomahe.pulsarsourceapp.message.control;

import io.quarkus.runtime.ShutdownEvent;
import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import java.util.Optional;

@ApplicationScoped
public class PulsarClientFactory {

    private static final Logger log = Logger.getLogger(PulsarClientFactory.class);

    @ConfigProperty(name = "pulsar.service-url")
    String serviceUrl;

    @ConfigProperty(name = "pulsar.tls-trust-cert")
    Optional<String> tlsTrustCert;
    /*@Inject
    @ConfigProperty(name = "pulsar.tls-cert-file")
    String tlsCertFile;
    @Inject
    @ConfigProperty(name = "pulsar.tls-key-file")
    String tlsKeyFile;*/

    PulsarClient pulsarClient;

    private void init() {
        try {
            ClientBuilder clientBuilder = PulsarClient.builder().serviceUrl(serviceUrl);
            if (tlsTrustCert.isPresent()) {
                clientBuilder = clientBuilder.tlsTrustCertsFilePath(tlsTrustCert.get());
            }
            ;
            this.pulsarClient = clientBuilder.build();
                    /*.tlsTrustCertsFilePath(tlsTrustCertsFile)
                    .authentication(
                            "org.apache.pulsar.client.impl.auth.AuthenticationTls",
                            "tlsCertFile:" + tlsCertFile + ",tlsKeyFile:" + tlsKeyFile
                    )*/
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
