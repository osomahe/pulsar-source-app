package net.osomahe.pulsarsourceapp.string.entity;

import net.osomahe.pulsarsourceapp.message.entity.TopicName;

import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

public class StringInfo {
    @TopicName
    public String topic;
    @NotEmpty
    public String data;

    public String producer;


    @Override
    public String toString() {
        return new StringJoiner(", ", StringInfo.class.getSimpleName() + "[", "]")
                .add("topic='" + topic + "'")
                .add("data='" + data + "'")
                .add("producer='" + producer + "'")
                .toString();
    }
}
