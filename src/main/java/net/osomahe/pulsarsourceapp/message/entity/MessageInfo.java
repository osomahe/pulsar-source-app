package net.osomahe.pulsarsourceapp.message.entity;

import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

public class MessageInfo {
    @TopicName
    public String topic;
    @NotEmpty
    public String data;

    public String producer;


    @Override
    public String toString() {
        return new StringJoiner(", ", MessageInfo.class.getSimpleName() + "[", "]")
                .add("topic='" + topic + "'")
                .add("data='" + data + "'")
                .add("producer='" + producer + "'")
                .toString();
    }
}
