package ru.project.wakepark.to;

import ru.project.wakepark.model.Pass;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.util.Objects;

public class QueueRowTo extends BaseTo {

    @NotNull
    protected final ControlQueueRow upOrDown;

    @NotNull
    protected final ControlQueueRow control;

    protected final boolean disControl;

    @NotNull
    protected final String name;

    @NotNull
    protected final Pass pass;

    @Min(1)
    protected final int count;

    @ConstructorProperties({"id", "up", "down", "control", "name", "count"})
    public QueueRowTo(Integer id, ControlQueueRow upOrDown, boolean disControl, @NotNull ControlQueueRow control, @NotNull String name, @NotNull Pass pass, @Min(1) int count) {
        super(null, id);
        this.upOrDown = upOrDown;
        this.disControl = disControl;
        this.control = control;
        this.name = name;
        this.pass = pass;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueueRowTo)) return false;
        QueueRowTo that = (QueueRowTo) o;
        return upOrDown == that.upOrDown &&
                count == that.count &&
                control == that.control &&
                name.equals(that.name);
    }

    public ControlQueueRow getUpOrDown() {
        return upOrDown;
    }

    public ControlQueueRow getControl() {
        return control;
    }

    public boolean isDisControl() {
        return disControl;
    }

    public String getName() {
        return name;
    }

    public Pass getPass() {
        return pass;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(upOrDown, control, name, count);
    }

    @Override
    public String toString() {
        return "QueueRowTo{" +
                "upOrDown=" + upOrDown +
                ", control=" + control +
                ", disControl=" + disControl +
                ", name='" + name + '\'' +
                ", pass=" + pass +
                ", count=" + count +
                '}';
    }
}
