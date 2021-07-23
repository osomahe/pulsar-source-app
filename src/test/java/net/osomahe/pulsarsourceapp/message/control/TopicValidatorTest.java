package net.osomahe.pulsarsourceapp.message.control;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TopicValidatorTest {

    private String[] ALLOWED_TOPIC_NAMES = {
            "persistent://public/default/my-topic", "persistent://public/default/my-topic", "default",
            "non-persistent://public/default/my-topic", "non-persistent://public/default/my-topic"
    };

    private String[] NOT_ALLOWED_TOPIC_NAMES = {
            null, "",
            "ersistent://public/default/my-topic", "persistent://public", "persistent://public//default",
            "public/default", "persistent://public/default/", "persistent:///public/default",
            "/default", "default/", "persistent://public/default", "non-persistent://public/default/",
            "persistent:///public/default/my-topic", "persistent://public/default/my-topic/"
    };

    private TopicValidator instance = new TopicValidator();

    @Test
    public void testAllowed() {
        for (String name : ALLOWED_TOPIC_NAMES) {
            Assertions.assertTrue(instance.isValid(name, null), "Value: " + name + " is valid topic name");
        }
    }

    @Test
    public void testNotAllowed() {
        for (String name : NOT_ALLOWED_TOPIC_NAMES) {
            Assertions.assertFalse(instance.isValid(name, null), "Value: " + name + " is not valid topic name");
        }
    }

}
