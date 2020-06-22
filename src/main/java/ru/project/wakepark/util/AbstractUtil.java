package ru.project.wakepark.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractUtil <S, To> {

    public List<To> getTos (Collection<S> s) {
        return s.stream().map(sl -> createTo(sl)).collect(Collectors.toList());
    }

    abstract To createTo(S s);

}
