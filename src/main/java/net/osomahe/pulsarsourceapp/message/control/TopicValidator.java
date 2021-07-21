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
        if(value == null || value.isEmpty()){
            return false;
        }
        if(value.startsWith(PERSISTENT)){
            String end = value.substring(PERSISTENT.length());
            int indexSlash = end.indexOf("/");
            return indexSlash > 0 && indexSlash < end.length() && indexSlash == end.lastIndexOf("/");
        }else if(value.startsWith(NON_PERSISTENT)){
            String end = value.substring(NON_PERSISTENT.length());
            int indexSlash = end.indexOf("/");
            return indexSlash > 0 && indexSlash < end.length() && indexSlash == end.lastIndexOf("/");
        }else if (!value.contains("/")){
            return true;
        }
        return false;
    }
}
