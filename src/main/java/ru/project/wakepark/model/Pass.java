package ru.project.wakepark.model;

public enum Pass {
    SEASON ("Абонемент"),
    SINGLE ("Разовый");

    private String title;

    Pass(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public static Pass fromString(String name) {
        for (Pass p : Pass.values()) {
            if (p.title.equalsIgnoreCase(name))
                return p;
        }
        return null;
    }
}
