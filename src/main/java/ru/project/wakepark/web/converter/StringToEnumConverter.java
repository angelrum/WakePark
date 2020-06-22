package ru.project.wakepark.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.project.wakepark.model.Pass;

public class StringToEnumConverter implements Converter<String, Pass> {

    @Override
    public Pass convert(String s) {
        try {
            return Pass.fromString(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
