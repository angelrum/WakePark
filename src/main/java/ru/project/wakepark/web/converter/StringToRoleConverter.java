package ru.project.wakepark.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.project.wakepark.model.Role;

public class StringToRoleConverter implements Converter<String, Role> {

    @Override
    public Role convert(String s) {
        return null;
    }
}
