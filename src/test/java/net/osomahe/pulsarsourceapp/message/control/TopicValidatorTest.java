package net.osomahe.pulsarsourceapp.message.control;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TopicValidatorTest {

    private String[] ALLOWED_TOPIC_NAMES = {
            "persistent://public/default", "persistent://public/default", "default"
    };

    private String[] NOT_ALLOWED_TOPIC_NAMES = {
            "ersistent://public/default", "persistent://public", "persistent://public//default",
            "public/default", "persistent://public/default/", "persistent:///public/default",
            "/default", "default/"
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
