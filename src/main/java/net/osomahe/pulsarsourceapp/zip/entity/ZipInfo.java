package net.osomahe.pulsarsourceapp.zip.entity;

import net.osomahe.pulsarsourceapp.message.entity.TopicName;

import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

public class ZipInfo {
    @TopicName
    public String topic;
    @NotEmpty
    public String zipBase64;

    public String producer;


    @Override
    public String toString() {
        return new StringJoiner(", ", ZipInfo.class.getSimpleName() + "[", "]")
                .add("topic='" + topic + "'")
                .add("producer='" + producer + "'")
                .toString();
    }
}
