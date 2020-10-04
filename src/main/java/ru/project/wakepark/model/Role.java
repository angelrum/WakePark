package ru.project.wakepark.model;

public enum Role {
    ADMIN ("Администратор"),
    MANAGER ("Менеджер"),
    USER ("Оператор");

    private String s;

    Role(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
