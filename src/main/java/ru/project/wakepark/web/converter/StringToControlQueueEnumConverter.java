package ru.project.wakepark.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.project.wakepark.util.ControlQueue;

public class StringToControlQueueEnumConverter implements Converter<String, ControlQueue> {

    @Override
    public ControlQueue convert(String s) {
        return ControlQueue.valueOf(s);
    }
}
