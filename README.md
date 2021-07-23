# Pulsar Source App

This application provides simple REST api for sending string message into Apache Pulsar topics. There is also simple html page to send message from browser.

REST API documentation is available [openapi.yml](openapi.yml) file.

## Installation

For simple start up you can use Docker image [https://hub.docker.com/repository/docker/osomahe/pulsar-source-app](https://hub.docker.com/repository/docker/osomahe/pulsar-source-app).

Release notes can be found at [releases.md](releases.md). 

Environment variables:

* **PULSAR_SERVICE_URL** - default "pulsar://localhost:6650" url to connect to Apache Pulsar instance
* **PULSAR_TLS_TRUST_CERT** - not set by default, used for transport encryption using tLS certificate e.g. `/pulsar/certs/ca.cert.pem`
  
Other environment variables:

* PULSAR_DEFAULT_PRODUCER - default "pulsar-source-app" name used for producing messages
* PULSAR_DEFAULT_TYPE - default "persistent" in case of simple topic name, message will be published into default namespace  persistent://public/default/topic-name
* PULSAR_DEFAULT_TENANT - default "public" in case of simple topic name, message will be published into default namespace  persistent://public/default/topic-name
* PULSAR_DEFAULT_NAMESPACE - default "default" in case of simple topic name, message will be published into default namespace  persistent://public/default/topic-name

Examples:
```bash
docker run -d --name pulsar-source-app -p 8080:8080 -e PULSAR_SERVICE_URL="pulsar://localhost:6650" osomahe/pulsar-source-app
```

### Health checks

* Liveness probe - `/q/health/live`
* Readiness probe - `/q/health/ready`

## Maintainers

This project was developed with support of companies [HP Tronic](http://www.hptronic.cz/) and [Osomahe](https://www.osomahe.com/).


## Development

Start Apache Pulsar instance.
```bash
docker run -d --rm --name pulsar \
-p 6650:6650 \
apachepulsar/pulsar:2.8.0 \
bin/pulsar standalone
```

Start Apache Pulsar instance with transport tls on.
```bash
docker run -d --rm --name pulsar -p 6651:6651 \
-v $(pwd)/development/pulsar/certs:/pulsar/certs:ro \
-v $(pwd)/development/pulsar/conf-transport-tls:/pulsar/conf:ro \
apachepulsar/pulsar:2.8.0 bin/pulsar standalone -nfw
```

Start Apache Pulsar instance with tls authorization.
```bash
docker run -d --rm --name pulsar -p 6651:6651 \
-v $(pwd)/development/pulsar/certs:/pulsar/certs:ro \
-v $(pwd)/development/pulsar/conf-auth-tls:/pulsar/conf:ro \
apachepulsar/pulsar:2.8.0 bin/pulsar standalone -nfw

docker exec -i pulsar bin/pulsar-admin namespaces grant-permission public/default --actions produce,consume --role pulsar-source-app
```