docker run -d --rm --name pulsar \
-p 6650:6650 \
apachepulsar/pulsar:2.8.0 \
bin/pulsar standalone

docker run -d --rm --name pulsar \
-p 6650:6650 -p 8080:8080 \
apachepulsar/pulsar:2.8.0 \
bin/pulsar standalone