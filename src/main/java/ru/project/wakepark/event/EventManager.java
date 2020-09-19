package ru.project.wakepark.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class EventManager {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private Map<String, Set<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String value : operations)
            listeners.put(value, new HashSet<>());
    }

    public EventManager() {
    }

    public void subscribe(String operation, EventListener listener) {
        Set<EventListener> event = listeners.computeIfAbsent(operation, key -> new HashSet<>());
        event.add(listener);
    }

    public void send(int companyId) {
        listeners.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(el->el.update(companyId));
    }

    public void send(int companyId, String operation) {
        listeners.get(operation).forEach(el -> el.update(companyId));
    }
}
