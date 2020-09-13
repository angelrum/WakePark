package ru.project.wakepark.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.project.wakepark.to.ControlQueueRow;

@Deprecated
public class StringToControlRowEnumConverter implements Converter<String, ControlQueueRow> {

    @Override
    public ControlQueueRow convert(String s) {
//        return ControlQueueRow.valueOf(s.toUpperCase());
//        try {
//            return ControlQueueRow.fromString(s);
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
        return null;
    }
}
