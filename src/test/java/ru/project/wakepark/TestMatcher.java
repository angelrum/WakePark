package ru.project.wakepark;

import org.assertj.core.api.IterableAssert;
import org.assertj.core.api.RecursiveComparisonAssert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMatcher<T> {
    private final Class<T> clazz;
    private final String[] fieldsToIgnore;
    private final boolean usingEquals;
    private final Map<Comparator, Class> comp;

    private TestMatcher(Class<T> clazz, boolean usingEquals, Map<Comparator, Class> comp, String... fieldsToIgnore) {
        this.clazz = clazz;
        this.fieldsToIgnore = fieldsToIgnore;
        this.usingEquals = usingEquals;
        this.comp = comp;
    }


    public static <T> TestMatcher<T> usingEquals(Class<T> clazz) {
        return new TestMatcher<>(clazz, true, null);
    }

    public static <T> TestMatcher<T> usingFieldsComparator(Class<T> clazz, String... fieldsToIgnore) {
        return new TestMatcher<>(clazz, false, null, fieldsToIgnore);
    }

    public static <T> TestMatcher<T> usingClassComparator(Class<T> clazz, Map<Comparator, Class> comp, String... fieldsToIgnore) {
        return new TestMatcher<T>(clazz, false, comp, fieldsToIgnore);
    }

    public void assertMatch(T actual, T expected) {
        if (usingEquals) {
            assertThat(actual).isEqualTo(expected);
        } else if(Objects.nonNull(comp)) {
            assertWithComparator(actual, expected);
        }else {
            assertThat(actual).isEqualToIgnoringGivenFields(expected, fieldsToIgnore);
        }
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        if (usingEquals) {
            assertThat(actual).isEqualTo(expected);
        } else if(Objects.nonNull(comp)) {
            assertWithComparator(actual, expected);
        } else {
            assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
        }
    }

    private void assertWithComparator(T actual, T expected) {
        RecursiveComparisonAssert<?> o = assertThat(actual).usingRecursiveComparison();
        o = (fieldsToIgnore.length > 0) ? o.ignoringFields(fieldsToIgnore) : o;

        for (Map.Entry<Comparator, Class> entry : comp.entrySet()) {
            Comparator cmp = entry.getKey();
            Class cl = entry.getValue();
            o = o.withComparatorForType(cmp, cl);
        }
        o.isEqualTo(expected);
    }

    private void assertWithComparator(Iterable<T> actual, Iterable<T> expected) {
        IterableAssert<T> o = assertThat(actual);
        o = (fieldsToIgnore.length > 0) ? o.usingElementComparatorIgnoringFields(fieldsToIgnore) : o;

        for (Map.Entry<Comparator, Class> entry : comp.entrySet()) {
            Comparator cmp = entry.getKey();
            Class cl = entry.getValue();
            o = o.usingComparatorForElementFieldsWithType(cmp, cl);
        }
        o.containsAll(expected);
    }
}
