package net.osomahe.pulsarsourceapp.message.control;

import net.osomahe.pulsarsourceapp.message.entity.TopicName;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@ApplicationScoped
public class TopicValidator implements ConstraintValidator<TopicName, String> {
    private static final String PERSISTENT = "persistent://";
    private static final String NON_PERSISTENT = "non-persistent://";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (value.startsWith(PERSISTENT)) {
            return validateWithPrefix(value, PERSISTENT);
        } else if (value.startsWith(NON_PERSISTENT)) {
            return validateWithPrefix(value, NON_PERSISTENT);
        } else if (!value.contains("/")) {
            return true;
        }
        return false;
    }

    private boolean validateWithPrefix(String topicName, String prefix) {
        if (topicName.endsWith("/")) return false;
        String end = topicName.substring(prefix.length());
        String[] parts = end.split("/");
        if (parts.length != 3) return false;
        for (String part : parts) {
            if (part.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
